package com.fortinet.fcasb.watcher.alert.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fortinet.fcasb.watcher.alert.domain.Monitor;
import com.fortinet.fcasb.watcher.alert.domain.MonitorMetric;
import com.fortinet.fcasb.watcher.alert.init.RestWrapper;
import com.fortinet.fcasb.watcher.alert.repo.MonitorMetricRepositoryImpl;
import com.fortinet.fcasb.watcher.alert.repo.MonitorRepositoryImpl;
import com.fortinet.fcasb.watcher.alert.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by zliu on 17/3/3.
 */

public abstract class AbstractMonitorHttpTask implements ITask {


    @Autowired
    private MonitorRepositoryImpl monitorDao;
    @Autowired
    private MonitorMetricRepositoryImpl monitorMetricDao;

    @Autowired
    private RestWrapper restWrapper;

    public void execute(){
        List<Monitor> monitors = monitorDao.findByType(getType());
        monitors.addAll(getMonitors());
        this.LOGGER.info("monitor list size {}",monitors.size());
        List<MonitorMetric> metrics =  monitorMetricDao.findByType(getType());
        this.LOGGER.info("metric list size {}",metrics.size());

        for(Monitor monitor:monitors) {
            for (MonitorMetric metric : metrics) {
                try {
                    String url = monitor.getMethod()+"://"+monitor.getHost()+":"+monitor.getPort()+metric.getUri();
                    ResponseEntity<Map<String, Object>> re = restWrapper.get(url, new TypeReference<Map<String, Object>>() {
                    });
                    this.LOGGER.info("url={}, result={}", metric.getUri(), JSON.toJSONString(re.getStatusCode()));
                    if (re.getBody() != null) {
                        Map<String, Object> body = re.getBody();
                        body.put("metrics", metric.getName());
                        body.put("m_host", monitor.getHost());
                        body.put("m_port", monitor.getPort());
                        body.put("name", monitor.getName());
                        body.put("m_label", monitor.getLabel());
                        body.put("filter", monitor.getType());
                        if(body.get("hostname")==null){
                            body.put("hostname", monitor.getHost());
                        }
                        if (!body.containsKey("timestamp")) {
                            body.put("timestamp", System.currentTimeMillis());
                        }
                        LogUtil.LOGGER_MONITOR_STATISTIC.info(JSON.toJSONString(body));
                    }
                }catch (Exception ex){
                    this.LOGGER.error("get {} value failed ",metric.getUri(),ex);

                }
            }
        }
    }

    @Override
    public void run() {
        this.execute();
    }


}
