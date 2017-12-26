package com.fortinet.fcasb.watcher.alert.service;

import com.fortinet.fcasb.watcher.alert.domain.Alert;
import com.fortinet.fcasb.watcher.alert.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zliu on 17/3/3.
 */

@Service
public class AlertTask  implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlertTask.class);
    @Value("${send.mail.list}")
    private String notemmail;

    @Value("${alert.period}")
    private Integer period;

    @Autowired
    private AlertService alertService;
    @Autowired
    private EmailService emailService;

    public void execute(){
        List<Alert> alertList =alertService.getAlertList();
        LOGGER.info("alert running {}",alertList.size());
        Date endTime = StringUtil.getCurrentWholeMinTime();
        Date startTime =  new Date(endTime.getTime() - period * 1000);

        for (Alert alert:alertList){
            LOGGER.info("alert {}",alert.getName());
            try {
                AlertService.ResultTarget resultTarget = alertService.isTarget(alert,startTime,endTime);
                if (resultTarget.isTarget()) {
                    LOGGER.warn("alert target {}", resultTarget.isTarget());
                    sendAlert(resultTarget.getAlert(), resultTarget.getValue(), resultTarget.getCount());
                }
            }catch (Exception ex){
                ex.printStackTrace();
                LOGGER.error("es search error {}",ex.toString());
            }
        }
    }

    @Override
    public void run() {
        this.execute();
    }

    public void sendAlert(Alert alert,String value,long count){
        //default template
        String title = alert.getName()+" "+alert.getIndex()+" Alert!!!";
        //default template
        String content = MessageFormat.format("Value {0} > {1}, Count {2} > {3}", value, alert.getConditionvalue(), count,
                alert.getConditioncount());

        if(StringUtils.isNotBlank(alert.getEmailtemplate()) ){
            content = alert.getEmailtemplate().replace("{count}", String.valueOf(count));
            if(StringUtils.isNotBlank(value)) {
                content = content.replace("{value}", value);
            }
        }
        if(StringUtils.isNotBlank(alert.getEmailtitle())){
            title = alert.getEmailtitle().replace("{count}", String.valueOf(count));
            if(StringUtils.isNotBlank(value)) {
                title = title.replace("{value}", value);
            }        }
        String to = alert.getNotifications()+","+notemmail;
        LOGGER.info("title= {}, content={}, to={}",title,content,to);
        emailService.sendMail(title, content, to);
    }
}
