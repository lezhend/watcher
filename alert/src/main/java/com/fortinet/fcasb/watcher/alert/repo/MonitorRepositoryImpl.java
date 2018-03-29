package com.fortinet.fcasb.watcher.alert.repo;

import com.fortinet.fcasb.watcher.alert.domain.Alert;
import com.fortinet.fcasb.watcher.alert.domain.Monitor;
import com.fortinet.fcasb.watcher.alert.enums.MonitorTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zliu on 12/28/16.
 */
public interface MonitorRepositoryImpl extends JpaRepository<Monitor, String> {
    Monitor getOneById(long id);
    Monitor getByName(String name);
    List<Monitor> findAll();
    List<Monitor> findByType(MonitorTypeEnum type);
    long countByName(String name);
}