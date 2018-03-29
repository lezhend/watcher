package com.fortinet.fcasb.watcher.alert.init;

import com.fortinet.fcasb.watcher.alert.domain.MonitorMetric;
import com.fortinet.fcasb.watcher.alert.enums.MonitorTypeEnum;
import com.fortinet.fcasb.watcher.alert.repo.MonitorMetricRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by zliu on 17/3/3.
 * 1.Init databases tables
 * 2.create tables on everyday
 */
@Service
public class InitDatabase {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitDatabase.class);


    @Autowired
    private AlertProperties alertProperties;

    @Autowired
    private MonitorMetricRepositoryImpl metricDao;


    @PostConstruct
    private void init(){
        defaultData();
    }

    private void defaultData(){
        MonitorMetric metric =new MonitorMetric();
        metric.setUri("/_cluster/health");
        metric.setName("es_cluster_health");
        metric.setType(MonitorTypeEnum.ES);
        try {
            metricDao.save(metric);
        } catch (Exception e) {

        }

        MonitorMetric metricStats =new MonitorMetric();
        metricStats.setUri("/_cluster/stats");
        metricStats.setName("es_cluster_stats");
        metricStats.setType(MonitorTypeEnum.ES);
        try {
            metricDao.save(metricStats);
        } catch (Exception e) {

        }

        MonitorMetric metricLogstash =new MonitorMetric();
        metricLogstash.setType(MonitorTypeEnum.LOGSTASH);
        metricLogstash.setUri("/_node/stats/process");
        metricLogstash.setName("logstash_stats_process");
        try {
            metricDao.save(metricLogstash);
        } catch (Exception e) {

        }
        MonitorMetric metricLogstashJVM =new MonitorMetric();
        metricLogstashJVM.setUri("/_node/stats/stats_jvm");
        metricLogstashJVM.setName("logstash_stats_jvm");
        metricLogstashJVM.setType(MonitorTypeEnum.LOGSTASH);
        try {
            metricDao.save(metricLogstashJVM);
        } catch (Exception e) {

        }
    }

}
