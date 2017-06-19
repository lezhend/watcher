package com.fortinet.fcasb.watcher.alert.service;

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

    @Value("${alert.period}")
    private Integer period;
    @Value("${monitor.es.period}")
    private Integer monitorESPeriod;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Autowired
    private ThreadPoolTaskScheduler monitorScheduler;

    @Autowired
    private  AlertTask alertTask;
    @Autowired
    private  MonitorESTask monitorESTask;
    @PostConstruct
    private void startTask() {
        int min = period/60;
        taskScheduler.schedule(alertTask, new CronTrigger("0 0/" + min + " * * * ? "));

        min = monitorESPeriod/60;
        monitorScheduler.schedule(monitorESTask, new CronTrigger("0 0/" + min + " * * * ? "));

    }

}
