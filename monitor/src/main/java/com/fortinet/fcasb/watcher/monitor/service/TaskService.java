package com.fortinet.fcasb.watcher.monitor.service;

import com.fortinet.fcasb.watcher.monitor.domain.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by zliu on 17/3/3.
 * 1.每天清理数据,创建数据库表
 * 2.按照monitor.properties中的周期配置获取信息
 */
@EnableScheduling
@Component
public class TaskService {
    @Value("${monitor.system.metrics}")
    private List<String> systemMetrics;

    @Value("${monitor.period}")
    private Integer monitorPeriod;

    @Value("${monitor.user.progress.filters}")
    private List<String> userDefaultFilters;

    @Value("${monitor.user.progress.metrics}")
    private List<String> userProgressType;


    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;


    @PostConstruct
    private void startTask() {
        int min = monitorPeriod/60;
        for(String metrics : systemMetrics) {
            taskScheduler.schedule(new SystemTask(Statistics.Metrics.SYSTEM.valueOf(metrics)), new CronTrigger("0 0/" + min + " * * * ? "));
        }
        for(String metrics : userProgressType) {
            for(String filter:userDefaultFilters) {
                taskScheduler.schedule(new ProgressTask(Statistics.Metrics.SYSTEM.valueOf(metrics),filter), new CronTrigger("0 0/" + min + " * * * ? "));
            }
        }
    }






}
