package com.fortinet.fcasb.watcher.monitor.service;

import com.fortinet.fcasb.watcher.monitor.dao.StatisticsDao;
import com.fortinet.fcasb.watcher.monitor.domain.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zliu on 17/3/3.
 */
public class ProgressTask implements ITask, Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProgressTask.class);

    private Statistics.Metrics.PROGRESS metrics;
    private String processFilter;

    @Autowired
    private StatisticsDao statisticsDao;

    public ProgressTask(Statistics.Metrics.PROGRESS metrics,String processFilter){
        this.metrics = metrics;
        this.processFilter = processFilter;
    }

    @Override
    public void run() {
        Statistics statistics = getInfo();
        execute(statistics);
    }

    @Override
    public void execute(Statistics statistics){
        if(statistics!=null) {
            LOGGER.info("[statistic] {}",statistics.toString());
            statisticsDao.insert(statistics);
        }else{
            LOGGER.error("[statistic] progress  {} value='0' ",this.metrics.toString());
        }
    }

    @Override
    public Statistics getInfo(){
        Statistics statistics = null;

        if (this.metrics.equals(Statistics.Metrics.PROGRESS.CPUUtilization)) {
            //todo
        }
        if (this.metrics.equals(Statistics.Metrics.PROGRESS.MEMCPUUtilization)) {
            //todo
        }
        if (this.metrics.equals(Statistics.Metrics.PROGRESS.Runtime)) {
            //todo
        }
        return statistics;
    }
}
