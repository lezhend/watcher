package com.fortinet.fcasb.watcher.alert.service;

import com.fortinet.fcasb.watcher.alert.init.AlertProperties;
import com.fortinet.fcasb.watcher.alert.task.MonitorESTask;
import com.fortinet.fcasb.watcher.alert.task.MonitorLogstashTask;
import com.fortinet.fcasb.watcher.alert.task.MonitorServiceTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by zliu on 17/3/3.
 * 1.每天清理数据,创建数据库表
 * 2.按照monitor.properties中的周期配置获取信息
 */
@EnableScheduling
@Component
public class TaskService  {

    @Autowired
    private AlertProperties alertProperties;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Autowired
    private ThreadPoolTaskScheduler monitorScheduler;
    @Autowired
    private ThreadPoolTaskScheduler logstashScheduler;
    @Autowired
    private ThreadPoolTaskScheduler serviceScheduler;

    @Autowired
    private  AlertTask alertTask;
    @Autowired
    private MonitorESTask monitorESTask;
    @Autowired
    private MonitorLogstashTask logstashTask;
    @Autowired
    private MonitorServiceTask serviceTask;
    @PostConstruct
    private void startTask() {
        int min = alertProperties.getAlertPeriod()/60;
        taskScheduler.schedule(alertTask, new CronTrigger("0 0/" + min + " * * * ? "));

        monitorScheduler.schedule(monitorESTask, new CronTrigger("0 0/" + min + " * * * ? "));

        logstashScheduler.schedule(logstashTask,new CronTrigger("0 0/" + min + " * * * ? "));
        serviceScheduler.schedule(serviceTask,new CronTrigger("0 0/" + min + " * * * ? "));
    }

}
