package com.fortinet.fcasb.watcher.alert.domain;

import com.fortinet.fcasb.watcher.alert.enums.MonitorTypeEnum;

import java.io.Serializable;

/**
 * Created by zliu on 17/3/3.
 */
public class MonitorMetric implements Serializable{
    private static final long serialVersionUID = 8441912850429111907L;
    private String name;       // name
    private String uri;       // url

    private MonitorTypeEnum type;      //es,logstash,kafka,kibana, server

    private String createTime;
    private String updateTime;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public MonitorTypeEnum getType() {
        return type;
    }

    public void setType(MonitorTypeEnum type) {
        this.type = type;
    }
}
