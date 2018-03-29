package com.fortinet.fcasb.watcher.alert.init;

import com.fortinet.fcasb.watcher.alert.service.AlertService;
import com.fortinet.fcasb.watcher.alert.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * Created by zliu on 17/3/3.
 * 1.Init databases tables
 * 2.create tables on everyday
 */
@Service
public class CleanDatabase {
    private static final Logger LOGGER = LoggerFactory.getLogger(CleanDatabase.class);


    @Autowired
    private AlertService alertService;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @PostConstruct
    public void init(){
        taskScheduler.schedule(()->this.autoCleanAlertLog(), new CronTrigger("0 0 12 * * ? "));
    }

    public void autoCleanAlertLog(){
        try {
            Date date = StringUtil.getCurrentWholeDayTime();
            Date deleteDate = new Date(date.getTime() - 1000 * 60 * 60 * 24 * 10);
            LOGGER.info("auto delete alert log {}",StringUtil.getLogTimestamp(deleteDate));
            alertService.delete(deleteDate);
        }catch (Exception ex){
            LOGGER.error("auto delete error ",ex);
        }
    }
}


