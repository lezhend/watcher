package com.fortinet.fcasb.watcher.monitor.service;

import com.fortinet.fcasb.watcher.monitor.dao.StatisticsDao;
import com.fortinet.fcasb.watcher.monitor.domain.Statistics;
import com.fortinet.fcasb.watcher.monitor.utils.StringUtil;
import com.fortinet.fcasb.watcher.monitor.utils.SystemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by zliu on 17/3/3.
 */
@Component
@Scope("prototype")
public class ProgressTask extends AbstractStatisticsTask implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProgressTask.class);

    private Statistics.Metrics.PROGRESS metrics;
    private String processFilter;

    @Value("${system.host.name}")
    private String name;
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
        execute();
    }


    @Override
    public Statistics getInfo(){
        Statistics statistics = new Statistics();
        statistics.setType(Statistics.Type.Progress.toString());
        statistics.setMetrics(this.metrics.toString());
        statistics.setTime(StringUtil.getStatisticsTime(new Date()));
        statistics.setFilter(this.processFilter);
        statistics.setTimestamp();
        statistics.setHostname(SystemUtil.getHostname());
        statistics.setName(name == null ? SystemUtil.getHostname():name);
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

    @Override
    public StatisticsDao getStatisticsDao() {
        return statisticsDao;
    }
}
