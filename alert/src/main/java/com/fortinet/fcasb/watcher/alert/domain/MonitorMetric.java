package com.fortinet.fcasb.watcher.alert.domain;

import com.fortinet.fcasb.watcher.alert.enums.MonitorTypeEnum;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by zliu on 17/3/3.
 */
@Entity
@Table(name = "monitor_metric")
public class MonitorMetric extends BaseEntity{
    private static final long serialVersionUID = 8441912850429111907L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column
    private String name;       // name
    @Column
    private String uri;       // url

    @Column
    @Enumerated(EnumType.ORDINAL)
    private MonitorTypeEnum type;      //es,logstash,kafka,kibana, server

    @Column(name="description",columnDefinition = "text")
    private String desc;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
