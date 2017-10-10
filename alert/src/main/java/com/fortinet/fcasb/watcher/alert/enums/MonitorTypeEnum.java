package com.fortinet.fcasb.watcher.alert.enums;

import com.fortinet.fcasb.watcher.alert.service.MonitorESTask;
import com.fortinet.fcasb.watcher.alert.service.MonitorLogstashTask;

/**
 * Created by zliu on 17/3/3.
 */
public enum MonitorTypeEnum {
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
