package com.fortinet.fcasb.watcher.monitor.enums;

/**
 * Created by zliu on 17/3/3.
 */
public enum  TablesEnum {
    STATISTICS("statistics");

    private String tableName;
    private TablesEnum (String tableName){
        this.tableName = tableName;
    }

    public String getTablename(){
        return this.tableName;
    }
}
