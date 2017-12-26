package com.fortinet.fcasb.watcher.alert.task;

import com.fortinet.fcasb.watcher.alert.domain.Monitor;
import com.fortinet.fcasb.watcher.alert.enums.MonitorTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by zliu on 17/10/11.
 */
public interface ITask extends Runnable {
    public List<Monitor> getMonitors();
    public MonitorTypeEnum getType();
    
    public Logger LOGGER = LoggerFactory.getLogger(ITask.class);
}
