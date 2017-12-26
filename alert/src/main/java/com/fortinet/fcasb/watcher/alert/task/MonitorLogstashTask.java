package com.fortinet.fcasb.watcher.alert.task;

import com.fortinet.fcasb.watcher.alert.domain.Monitor;
import com.fortinet.fcasb.watcher.alert.enums.MonitorTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zliu on 17/3/3.
 */

@Service
public class MonitorLogstashTask extends AbstractMonitorHttpTask {
    public Logger LOGGER = LoggerFactory.getLogger(MonitorLogstashTask.class);

    @Value("${logstash.server.hosts}")
    private String[] logstashHosts;
    @Value("${logstash.server.names}")
    private String[] logstashNames;

    private List<Monitor> MONITOR_LIST = new ArrayList();


    @PostConstruct
    private void init(){
//        metrics.put("stats_process","/_node/stats/process") ;
//        metrics.put("stats_jvm","/_node/stats/jvm") ;
        for(int i=0;i<logstashHosts.length;i++){
            String host=logstashHosts[i];
            String port="80";
            if(logstashHosts[i].contains(":")){
                String[] urls = logstashHosts[i].split(":");
                host = urls[0];
                port = urls[1];
            }
            Monitor monitor = new Monitor();
            monitor.setLabel("default-"+i);
            monitor.setName(logstashNames[i]);
            monitor.setPort(port);
            monitor.setHost(host);
            monitor.setMethod("http");
            monitor.setType(MonitorTypeEnum.LOGSTASH);
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
        return MonitorTypeEnum.LOGSTASH;
    }
}
