package com.fortinet.fcasb.watcher.monitor.service;

import com.fortinet.fcasb.watcher.monitor.dao.StatisticsDao;
import com.fortinet.fcasb.watcher.monitor.domain.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zliu on 17/3/3.
 */
public class SystemTask implements Runnable,ITask {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemTask.class);

    private Statistics.Metrics.SYSTEM metrics;

    @Autowired
    private StatisticsDao statisticsDao;

    public SystemTask(Statistics.Metrics.SYSTEM metrics){
        this.metrics = metrics;
    }

    @Override
    public void run() {
        Statistics statistics = getInfo();
        save(statistics);
    }

    @Override
    public void save(Statistics statistics){
        if(statistics!=null) {
            LOGGER.info(statistics.toString());
            statisticsDao.insert(statistics);
        }else{
            LOGGER.error("system task get ? info failed ",this.metrics.toString());
        }
    }

    @Override
    public Statistics getInfo(){
        Statistics statistics = null;
        if(this.metrics.equals(Statistics.Metrics.SYSTEM.CPUUtilization)){
            //todo
        }
        if(this.metrics.equals(Statistics.Metrics.SYSTEM.MEMCPUUtilization)){
            //todo
        }
        if(this.metrics.equals(Statistics.Metrics.SYSTEM.Runtime)){
            //todo
        }
        return statistics;
    }


}
