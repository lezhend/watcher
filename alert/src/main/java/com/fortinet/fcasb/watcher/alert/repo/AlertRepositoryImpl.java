package com.fortinet.fcasb.watcher.alert.repo;

import com.fortinet.fcasb.watcher.alert.domain.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zliu on 12/28/16.
 */
public interface AlertRepositoryImpl extends JpaRepository<Alert, String> {
    Alert getOneById(long id);
    Alert getByName(String name);
    List<Alert> findAll();
    long countByName(String name);
}