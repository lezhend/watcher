package com.fortinet.fcasb.watcher.alert.enums;

/**
 * Created by zliu on 17/3/3.
 */
public enum  TablesEnum {
    ALERT("alert"),
    ALERT_LOG("alert_log"),
    MONITOR("monitor"),
    MONITOR_METRIC("monitor_metric"),
    STATISTICS("statistics");

    private String tableName;
    private TablesEnum (String tableName){
        this.tableName = tableName;
    }

    public String getTablename(){
        return this.tableName;
    }
}
