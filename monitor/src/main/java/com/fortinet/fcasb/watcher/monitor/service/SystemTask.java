package com.fortinet.fcasb.watcher.monitor.service;

import com.fortinet.fcasb.watcher.monitor.dao.StatisticsDao;
import com.fortinet.fcasb.watcher.monitor.domain.Statistics;
import com.fortinet.fcasb.watcher.monitor.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by zliu on 17/3/3.
 */

@Component
@Scope("prototype")
public class SystemTask implements Runnable,ITask {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemTask.class);

    private Statistics.Metrics.SYSTEM metrics;
    private String filter;

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
        Statistics statistics = new Statistics();
        statistics.setFilter(filter);
        statistics.setType(Statistics.Type.System.toString());
        statistics.setMetrics(this.metrics.toString());
        statistics.setTime(StringUtil.getStatisticsTime(new Date()));
        if(this.metrics.equals(Statistics.Metrics.SYSTEM.CPUUtilization)){
            statistics.setValue(systemService.getUseCPUInfo());
        }
        if(this.metrics.equals(Statistics.Metrics.SYSTEM.MEMCPUUtilization)){
           statistics.setValue(systemService.getUseMEMInfo());
        }
        if(this.metrics.equals(Statistics.Metrics.SYSTEM.Runtime)){
            Long time = systemService.getRuntime();
            if(time!=null){
                statistics.setValue(String.valueOf(time));
            } else{
                statistics.setValue("-1");
            }
        } else{
            statistics = null;
        }
        if(statistics!=null && StringUtils.isEmpty(statistics.getValue())){
            statistics.setValue("0");
        }
        return statistics;
    }


}
