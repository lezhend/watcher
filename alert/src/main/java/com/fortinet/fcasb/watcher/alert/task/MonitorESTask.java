package com.fortinet.fcasb.watcher.alert.task;

import com.fortinet.fcasb.watcher.alert.domain.Monitor;
import com.fortinet.fcasb.watcher.alert.enums.MonitorTypeEnum;
import com.fortinet.fcasb.watcher.alert.init.AlertProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zliu on 17/3/3.
 */

@Service
public class MonitorESTask extends AbstractMonitorHttpTask {
    public Logger LOGGER = LoggerFactory.getLogger(MonitorESTask.class);

    @Autowired
    private AlertProperties alertProperties;


    private List<Monitor> MONITOR_LIST = new ArrayList();

    @PostConstruct
    private void init(){
//        metrics.put("cluster_health","/_cluster/health") ;
//        metrics.put("cluster_stats","/_cluster/stats") ;
        String[] esHosts = alertProperties.getEsHosts().split(",");
        String[] esPorts = alertProperties.getEsPorts().split(",");
        for(int i=0;i<esHosts.length;i++){
            Monitor monitor = new Monitor();
            monitor.setLabel("default-"+i);
            monitor.setName(esHosts[i]);
            monitor.setPort(esPorts[i]);
            monitor.setHost(esHosts[i]);
            monitor.setMethod("http");
            monitor.setType(MonitorTypeEnum.ES);
            MONITOR_LIST.add(monitor);
        }

    }


    @Override
    public void run() {
        this.execute();
    }


    @Override
    public List<Monitor> getMonitors() {
        return MONITOR_LIST;
    }

    @Override
    public MonitorTypeEnum getType() {
        return MonitorTypeEnum.ES;
    }
}
