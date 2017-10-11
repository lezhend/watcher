package com.fortinet.fcasb.watcher.alert.task;

import com.fortinet.fcasb.watcher.alert.domain.Monitor;
import com.fortinet.fcasb.watcher.alert.enums.MonitorTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zliu on 17/3/3.
 */

@Service
public class MonitorSBTask extends AbstractMonitorHttpTask {
    public Logger LOGGER = LoggerFactory.getLogger(MonitorSBTask.class);


    private List<Monitor> MONITOR_LIST = new ArrayList();

    @Override
    public void run() {
        this.execute();
    }


    @Override
    public List<Monitor> getMonitors() {
        return MONITOR_LIST;
    }

    @Override
    public MonitorTypeEnum getType() {
        return MonitorTypeEnum.SPRING_BOOT;
    }
}
