package com.fortinet.fcasb.watcher.alert.service;

import com.fortinet.fcasb.watcher.alert.domain.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zliu on 17/3/3.
 */

@Service
public class AlertTask  implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlertTask.class);


    @Autowired
    private AlertService alertService;
    @Autowired
    private EmailService emailService;

    public void execute(){
        List<Alert> alertList =alertService.getAlertList();
        LOGGER.info("alert running {}",alertList.size());
        for (Alert alert:alertList){
            LOGGER.info("alert {}",alert.getName());
            AlertService.ResultTarget resultTarget = alertService.isTarget(alert);
            if(resultTarget.isTarget()){
                LOGGER.warn("alert target {}",resultTarget.isTarget());
                emailService.sendAlert(resultTarget.getAlert(),resultTarget.getValue(),resultTarget.getCount());
            }
        }
    }

    @Override
    public void run() {
        this.execute();
    }
}
