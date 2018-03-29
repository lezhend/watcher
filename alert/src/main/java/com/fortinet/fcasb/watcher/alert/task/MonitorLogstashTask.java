package com.fortinet.fcasb.watcher.alert.task;

import com.fortinet.fcasb.watcher.alert.domain.Monitor;
import com.fortinet.fcasb.watcher.alert.enums.MonitorTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zliu on 17/3/3.
 */

@Service
public class MonitorLogstashTask extends AbstractMonitorHttpTask {
    public Logger LOGGER = LoggerFactory.getLogger(MonitorLogstashTask.class);

    @Override
    public void run() {
        this.execute();
    }

    @Override
    public List<Monitor> getMonitors() {
        return new ArrayList<>();
    }

    @Override
    public MonitorTypeEnum getType() {
        return MonitorTypeEnum.LOGSTASH;
    }
}
