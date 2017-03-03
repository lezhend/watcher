package com.fortinet.fcasb.watcher.monitor.service;

import com.fortinet.fcasb.watcher.monitor.domain.Statistics;

/**
 * Created by zliu on 17/3/3.
 */
public interface ITask {
    Statistics getInfo();
    public void save(Statistics statistics);
}
