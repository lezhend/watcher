package com.fortinet.fcasb.watcher.alert.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
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
    
    private List<Map<String,String>> monitorUrls = new ArrayList();

    @PostConstruct
    private void init(){
        for(int i=0;i<logstashHosts.length;i++){
            Map<String,String> monitor = new HashMap<>();
            monitor.put("stats_process","http://"+logstashHosts[i]+"/_node/stats/process");
            monitor.put("stats_jvm","http://"+logstashHosts[i]+"/_node/stats/jvm");
            monitorUrls.add(monitor);
        }

//         monitorUrls.put("cluster_nodes","http://"+esHost+":"+esPort+"/_nodes");
    }

    @Autowired
    private RestWrapper restWrapper;

    public void execute(){
        for(int i =0;i<monitorUrls.size();i++) {
            for (Map.Entry<String, String> entry : monitorUrls.get(i).entrySet()) {
                try {
                    ResponseEntity<Map<String, Object>> re = restWrapper.get(entry.getValue(), new TypeReference<Map<String, Object>>() {
                    });
                    LOGGER.info("url={}, result={}", entry.getValue(), JSON.toJSONString(re.getStatusCode()));
                    if (re.getBody() != null) {
                        Map<String, Object> body = re.getBody();
                        body.put("filter", "logstash_node");
                        body.put("metrics", entry.getKey());
                        body.put("m_host", logstashHosts[i]);
                        body.put("server_name", logstashNames[i]);
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
