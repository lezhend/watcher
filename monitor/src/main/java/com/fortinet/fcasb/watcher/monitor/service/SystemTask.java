package com.fortinet.fcasb.watcher.monitor.service;

import com.fortinet.fcasb.watcher.monitor.dao.StatisticsDao;
import com.fortinet.fcasb.watcher.monitor.domain.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by zliu on 17/3/3.
 */

@Component
@Scope("prototype")
public class SystemTask implements Runnable,ITask {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemTask.class);

    private Statistics.Metrics.SYSTEM metrics;

    @Autowired
    private StatisticsDao statisticsDao;

    @Autowired
    private SystemService systemService;

    public void setMetrics(Statistics.Metrics.SYSTEM metrics){
        this.metrics = metrics;
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
            LOGGER.error("[statistic] system  {} value='0' ",this.metrics.toString());
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
            LOGGER.debug("Meminfo {}",systemService.getUseMEMInfo() );

        }
        if(this.metrics.equals(Statistics.Metrics.SYSTEM.Runtime)){
            //todo
        }
        return statistics;
    }


}
