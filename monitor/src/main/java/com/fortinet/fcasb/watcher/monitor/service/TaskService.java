package com.fortinet.fcasb.watcher.monitor.service;

import com.fortinet.fcasb.watcher.monitor.domain.Statistics;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
public class TaskService implements ApplicationContextAware {
    @Value("${monitor.system.metrics}")
    private String[] systemMetrics;

    @Value("${monitor.period}")
    private Integer monitorPeriod;

    @Value("${monitor.user.progress.filters}")
    private String[] userDefaultFilters;
    @Value("${monitor.user.progress.filter.cmds}")
    private String[] userDefaultCmds;

    @Value("${monitor.user.progress.metrics}")
    private String[] userProgressType;

    private ApplicationContext  applicationContext;


    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;


    @PostConstruct
    private void startTask() {
        int min = monitorPeriod/60;
        if(userDefaultFilters.length!=userDefaultCmds.length){
            throw new RuntimeException("files and cmds config error in the monitor.properties");
        }

        taskScheduler.schedule(new ManageTask(),new CronTrigger("0 0 1 * * ? "));

        for(String metrics : systemMetrics) {
            SystemTask systemTask = applicationContext.getBean(SystemTask.class);
            systemTask.setMetrics(Statistics.Metrics.SYSTEM.valueOf(metrics));
            taskScheduler.schedule(systemTask, new CronTrigger("0 0/" + min + " * * * ? "));

        }
        for(String metrics : userProgressType) {
            for(int i=0;i<userDefaultFilters.length;i++) {
                ProgressTask task = applicationContext.getBean(ProgressTask.class);
                task.setMetrics(Statistics.Metrics.PROGRESS.valueOf(metrics));
                task.setProcessFilter(userDefaultFilters[i]);
                task.setStartRun(userDefaultCmds[i]);
                taskScheduler.schedule(task, new CronTrigger("0 0/" + min + " * * * ? "));
            }
        }


    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
         this.applicationContext = applicationContext;
    }
}
