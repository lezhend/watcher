package com.fortinet.fcasb.watcher.monitor.domain;

import com.fortinet.fcasb.watcher.monitor.utils.StringUtil;

import java.io.Serializable;

/**
 * Created by zliu on 17/3/3.
 */
public class Statistics implements Serializable{

    private static final long serialVersionUID = -2047728067852756058L;
    private String type;
    private String metrics;
    private String filter;
    private String time;
    private String value;

    //system info
    private String timestamp;
    private String hostname;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setTimestamp(){
        this.timestamp = StringUtil.getLogTimestamp();
    }

    @Override
    public String toString() {
        return "" +
                " time='" + time + '\'' +
                ", type='" + type + '\'' +
                ", metrics='" + metrics + '\'' +
                ", filter='" + filter + '\'' +
                ", value='" + value + '\'';
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMetrics() {
        return metrics;
    }

    public void setMetrics(String metrics) {
        this.metrics = metrics;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public enum Type{
        System(),
        Progress();
    }

    public interface Metrics {
        public enum SYSTEM{
            CPUUtilization(),
            MEMCPUUtilization(),
            Runtime();

        }
        public enum PROGRESS{
            IS_RUNNING(),
            CPUUtilization(),
            MEMCPUUtilization(),
            Runtime();
        }
    }
}
