package com.fortinet.fcasb.watcher.alert.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by zliu on 17/3/3.
 */
@Entity
@Table(name = "monitor_alert_log")
public class AlertLog extends BaseEntity{

    private static final long serialVersionUID = -3431496450226286102L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column
    private String name;         //name
    @Column
    private long alertId;
    @Column(columnDefinition = "text")
    private String content;          //alert content
    @Column
    private String notifications;        //to email

    public long getAlertId() {
        return alertId;
    }

    public void setAlertId(long alertId) {
        this.alertId = alertId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNotifications() {
        return notifications;
    }

    public void setNotifications(String notifications) {
        this.notifications = notifications;
    }

}
