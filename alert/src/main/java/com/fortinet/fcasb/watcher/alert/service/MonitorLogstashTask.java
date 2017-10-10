package com.fortinet.fcasb.watcher.alert.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fortinet.fcasb.watcher.alert.dao.MonitorDao;
import com.fortinet.fcasb.watcher.alert.domain.Monitor;
import com.fortinet.fcasb.watcher.alert.enums.MonitorTypeEnum;
import com.fortinet.fcasb.watcher.alert.init.RestWrapper;
import com.fortinet.fcasb.watcher.alert.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zliu on 17/3/3.
 */

@Service
public class MonitorLogstashTask implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorLogstashTask.class);

    @Value("${logstash.server.hosts}")
    private String[] logstashHosts;
    @Value("${logstash.server.names}")
    private String[] logstashNames;

    private List<Monitor> MONITOR_LIST = new ArrayList();

    private Map<String,String> metrics= new HashMap<>();


    @Autowired
    private RestWrapper restWrapper;

    @PostConstruct
    private void init(){
        metrics.put("stats_process","/_node/stats/process") ;
        metrics.put("stats_jvm","/_node/stats/jvm") ;
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
            monitor.setType(MonitorTypeEnum.LOGSTASH.toString());
            MONITOR_LIST.add(monitor);
        }
    }

    @Autowired
    private MonitorDao monitorDao;

    public void execute(){
        List<Monitor> monitors = monitorDao.findByType(MonitorTypeEnum.LOGSTASH);
        monitors.addAll(MONITOR_LIST);
        for(Monitor monitor:monitors) {
            for (Map.Entry<String, String> entry : metrics.entrySet()) {
                try {
                    String url = monitor.getMethod()+"://"+monitor.getHost()+":"+monitor.getPort()+entry.getValue();

                    ResponseEntity<Map<String, Object>> re = restWrapper.get(url, new TypeReference<Map<String, Object>>() {
                    });
                    LOGGER.info("url={}, result={}", entry.getValue(), JSON.toJSONString(re.getStatusCode()));
                    if (re.getBody() != null) {
                        Map<String, Object> body = re.getBody();
                        body.put("filter", "logstash_node");
                        body.put("metrics", entry.getKey());
                        body.put("m_host", monitor.getHost());
                        body.put("m_name", monitor.getName());
                        body.put("m_label", monitor.getLabel());
                        body.put("m_type", monitor.getType());
                        if (!body.containsKey("timestamp")) {
                            body.put("timestamp", System.currentTimeMillis());
                        }
                        LogUtil.LOGGER_MONITOR_STATISTIC.info(JSON.toJSONString(body));
                    }
                }catch (Exception ex){
                    LOGGER.error("get {} value failed ",entry.getValue(),ex);
                }
            }
        }
    }

    @Override
    public void run() {
        this.execute();
    }


}
