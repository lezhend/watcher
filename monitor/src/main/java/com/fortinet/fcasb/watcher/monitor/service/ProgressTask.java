package com.fortinet.fcasb.watcher.monitor.service;

import com.fortinet.fcasb.watcher.monitor.dao.StatisticsDao;
import com.fortinet.fcasb.watcher.monitor.domain.Statistics;
import com.fortinet.fcasb.watcher.monitor.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by zliu on 17/3/3.
 */
@Component
@Scope("prototype")
public class ProgressTask implements ITask, Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProgressTask.class);

    private Statistics.Metrics.PROGRESS metrics;
    private String processFilter;

    @Autowired
    private StatisticsDao statisticsDao;

    @Autowired
    private SystemService systemService;

    public void setMetrics(Statistics.Metrics.PROGRESS metrics){
        this.metrics = metrics;
    }

    public void setProcessFilter(String processFilter){
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
        Statistics statistics = new Statistics();
        statistics.setType(Statistics.Type.Progress.toString());
        statistics.setMetrics(this.metrics.toString());
        statistics.setTime(StringUtil.getStatisticsTime(new Date()));
        statistics.setFilter(this.processFilter);
        if (this.metrics.equals(Statistics.Metrics.PROGRESS.CPUUtilization)) {
            String value = systemService.getProgressUseCPU(this.processFilter);
            if(value!=null){
                statistics.setValue(value);
            } else{
                statistics.setValue("0");
            }
        } else
        if (this.metrics.equals(Statistics.Metrics.PROGRESS.MEMCPUUtilization)) {
            String value = systemService.getProgressUseMEM(this.processFilter);
            if(value!=null){
                statistics.setValue(value);
            } else{
                statistics.setValue("0");
            }
        } else
        if (this.metrics.equals(Statistics.Metrics.PROGRESS.Runtime)) {
            String value = systemService.getProgressRuntime(this.processFilter);
            if(value!=null){
                statistics.setValue(value);
            }else{
                statistics.setValue("0");
            }
        } else if(this.metrics.equals(Statistics.Metrics.PROGRESS.IS_RUNNING)){
            Boolean value = systemService.progressIsRun(this.processFilter);
            statistics.setValue(value.toString());
        } else{
            statistics = null;
        }
        return statistics;
    }
}
