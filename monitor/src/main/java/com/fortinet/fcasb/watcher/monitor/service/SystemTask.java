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
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by zliu on 17/3/3.
 */

@Component
@Scope("prototype")
public class SystemTask extends AbstractStatisticsTask implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemTask.class);

    private Statistics.Metrics.SYSTEM metrics;
    private String filter;

    @Value("${system.host.name}")
    private String name;

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
        execute();
    }


    @Override
    public Statistics getInfo(){
        Statistics statistics = new Statistics();
        statistics.setFilter(filter);
        statistics.setType(Statistics.Type.System.toString());
        statistics.setMetrics(this.metrics.toString());
        statistics.setTime(StringUtil.getStatisticsTime(new Date()));
        statistics.setTimestamp();
        statistics.setHostname(SystemUtil.getHostname());
        statistics.setName(name == null ? SystemUtil.getHostname():name);
        if(this.metrics.equals(Statistics.Metrics.SYSTEM.CPUUtilization)){
            statistics.setValue(systemService.getUseCPUInfo());
        } else
        if(this.metrics.equals(Statistics.Metrics.SYSTEM.MEMCPUUtilization)){
           statistics.setValue(systemService.getUseMEMInfo());
        } else
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

    @Override
    public StatisticsDao getStatisticsDao() {
        return statisticsDao;
    }
}
