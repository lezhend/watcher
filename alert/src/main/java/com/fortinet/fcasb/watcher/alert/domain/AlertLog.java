package com.fortinet.fcasb.watcher.alert.domain;

import java.io.Serializable;

/**
 * Created by zliu on 17/3/3.
 */
public class AlertLog implements Serializable{

    private static final long serialVersionUID = -3431496450226286102L;
    private String name;         //name
    private String content;          //alert content
    private String notifications;        //to email
    private String createtime;         //createtime

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

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
