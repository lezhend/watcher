package com.fortinet.fcasb.watcher.alert.domain;

import com.fortinet.fcasb.watcher.alert.enums.MonitorTypeEnum;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by zliu on 17/3/3.
 */
@Entity
@Table(name = "monitor_content")
public class Monitor extends BaseEntity{
    private static final long serialVersionUID = 3725644924271403838L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(columnDefinition = "text")
    private String host;         //host name
    @Column
    private String port;         //port name
    @Column
    private String method;         //http,https,ssh, ...
    @Column(unique = true)
    private String name;          //define monitor server name , it is unique
    @Column
    private String label;         //define label name
    @Column
    @Enumerated(EnumType.ORDINAL)
    private MonitorTypeEnum type;         //es,logstash,kafka,kibana, server

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public MonitorTypeEnum getType() {
        return type;
    }

    public void setType(MonitorTypeEnum type) {
        this.type = type;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }




}
