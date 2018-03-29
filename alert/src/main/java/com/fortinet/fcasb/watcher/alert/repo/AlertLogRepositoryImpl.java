package com.fortinet.fcasb.watcher.alert.repo;

import com.fortinet.fcasb.watcher.alert.domain.Alert;
import com.fortinet.fcasb.watcher.alert.domain.AlertLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zliu on 12/28/16.
 */
public interface AlertLogRepositoryImpl extends JpaRepository<AlertLog, String> {
    List<AlertLog> findAll();
    List<AlertLog> findAllByAlertId(long alertId);
    void deleteAllByCreatedBefore(long createTime);
}