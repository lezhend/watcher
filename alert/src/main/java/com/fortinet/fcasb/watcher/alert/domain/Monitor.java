package com.fortinet.fcasb.watcher.alert.domain;

import java.io.Serializable;

/**
 * Created by zliu on 17/3/3.
 */
public class Monitor implements Serializable{
    private static final long serialVersionUID = 3725644924271403838L;
    private String host;            //host name
    private String port;           //port name
    private String method;         //http,https,ssh, ...
    
    private String name;          //define monitor server name , it is unique
    private String label;         //define label name

    private String type;         //es,logstash,kafka,kibana, server

    private String createTime;
    private String updateTime;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }




}
