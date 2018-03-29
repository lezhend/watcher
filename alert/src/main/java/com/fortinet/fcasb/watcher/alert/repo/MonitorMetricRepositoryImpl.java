package com.fortinet.fcasb.watcher.alert.repo;

import com.fortinet.fcasb.watcher.alert.domain.Monitor;
import com.fortinet.fcasb.watcher.alert.domain.MonitorMetric;
import com.fortinet.fcasb.watcher.alert.enums.MonitorTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zliu on 12/28/16.
 */
public interface MonitorMetricRepositoryImpl extends JpaRepository<MonitorMetric, String> {
    MonitorMetric getOneById(long id);
    MonitorMetric getByName(String name);
    List<MonitorMetric> findAll();
    List<MonitorMetric> findByType(MonitorTypeEnum type);
    long countByName(String name);
}