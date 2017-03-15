package com.fortinet.fcasb.watcher.alert.service;

import com.fortinet.fcasb.watcher.alert.domain.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        for (Alert alert:alertService.getAlertList()){
            if(alertService.isTarget(alert)){
                emailService.sendAlert(alert);
            }
        }
    }

    @Override
    public void run() {
        this.execute();
    }
}
