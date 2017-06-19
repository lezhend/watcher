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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zliu on 17/3/3.
 */

@Service
public class MonitorESTask implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorESTask.class);

    @Value("${es.server.host}")
    private String esHost;
    @Value("${es.server.port}")
    private String esPort;
    private Map<String,String> monitorUrls = new HashMap<>();

    @PostConstruct
    private void init(){
         monitorUrls.put("health","http://"+esHost+":"+esPort+"/_cluster/health");
         monitorUrls.put("stats","http://"+esHost+":"+esPort+"/_cluster/stats");
         monitorUrls.put("nodes","http://"+esHost+":"+esPort+"/_nodes");
    }

    @Autowired
    private RestWrapper restWrapper;

    public void execute(){
        for(Map.Entry<String,String> entry:monitorUrls.entrySet()){
            ResponseEntity<Map<String,Object>> re = restWrapper.get(entry.getValue(),new TypeReference<Map<String,Object>>(){});
            LOGGER.info("url={}, result={}",entry.getValue(),JSON.toJSONString(re.getBody()));
            if(re.getBody()!=null) {
                Map<String, Object> body = re.getBody();
                body.put("_type", entry.getKey());
                LogUtil.LOGGER_MONITOR_STATISTIC.info(JSON.toJSONString(body));
            }
        }
    }

    @Override
    public void run() {
        this.execute();
    }


}
