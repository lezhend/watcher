package com.fortinet.fcasb.watcher.monitor.service;

import com.fortinet.fcasb.watcher.monitor.dao.StatisticsDao;
import com.fortinet.fcasb.watcher.monitor.domain.Statistics;
import com.fortinet.fcasb.watcher.monitor.init.InitDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by zliu on 17/3/3.
 */
public class ManageTask implements ITask, Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageTask.class);

    private Statistics.Metrics.SYSTEM metrics;
    private String processFilter;

    @Autowired
    private StatisticsDao statisticsDao;

    @Autowired
    private InitDatabase initDatabase;

    @Override
    public void run() {
        Statistics statistics = getInfo();
        execute(statistics);
    }

    @Override
    public void execute(Statistics statistics){
        statisticsDao.dropTable(new Date(new Date().getTime() - 2 * 24 * 60 * 60 * 1000));
        initDatabase.createDayTables();
    }

    @Override
    public Statistics getInfo(){
        return null;
    }
}
