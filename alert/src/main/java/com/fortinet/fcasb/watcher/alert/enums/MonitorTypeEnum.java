package com.fortinet.fcasb.watcher.alert.enums;

import com.fortinet.fcasb.watcher.alert.task.MonitorESTask;
import com.fortinet.fcasb.watcher.alert.task.MonitorLogstashTask;
import com.fortinet.fcasb.watcher.alert.task.MonitorSBTask;
import com.fortinet.fcasb.watcher.alert.task.MonitorServiceTask;

/**
 * Created by zliu on 17/3/3.
 */
public enum MonitorTypeEnum {
    MONITOR_SERVICE(MonitorServiceTask.class),
    SPRING_BOOT(MonitorSBTask.class),
    ES(MonitorESTask.class),
    LOGSTASH(MonitorLogstashTask.class),
    KAFKA(MonitorLogstashTask.class);

    private Class<Runnable> clazz;
    MonitorTypeEnum(Class clazz){
        this.clazz = clazz;
    }

    public Class<Runnable> getClazz(){
        return this.clazz;
    }


}
