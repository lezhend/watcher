package com.fortinet.fcasb.watcher.alert.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zliu on 17/3/3.
 */
@Entity
@Table(name = "monitor_alert")
public class Alert extends BaseEntity{
    private static final long serialVersionUID = -4879308807404260222L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(columnDefinition = "text")
    private String host;         //host name
    @Column
    private String port;         //port name
    @Column(name="index_name")
    private String index;         //index name
    @Column(unique = true)
    private String name;          //alertName
    @Column(columnDefinition = "text")
    private String filter;        //search condition   field,key
    @Column
    private String searchkey;        //search condition
    @Column
    private String field;         //get field value
    @Column
    private String cvalue;      //alert gt lt le ge eq
    @Column
    private String conditionvalue;      //alert condition >field value
    @Column
    private String ccount;      //gt lt le ge eq
    @Column
    private String conditioncount;      //alert condition >count
    @Column
    private String notifications; //通知 , 分隔
    @Column
    private String emailtitle;

    @Column(columnDefinition = "text")
    private String emailtemplate; //


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getEmailtemplate() {
        return emailtemplate;
    }

    public void setEmailtemplate(String emailtemplate) {
        this.emailtemplate = emailtemplate;
    }

    public String getEmailtitle() {
        return emailtitle;
    }

    public void setEmailtitle(String emailtitle) {
        this.emailtitle = emailtitle;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearchkey() {
        return searchkey;
    }

    public void setSearchkey(String searchkey) {
        this.searchkey = searchkey;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getCvalue() {
        return cvalue;
    }

    public void setCvalue(String cvalue) {
        this.cvalue = cvalue;
    }


    public String getCcount() {
        return ccount;
    }

    public void setCcount(String ccount) {
        this.ccount = ccount;
    }

    public String getConditionvalue() {
        return conditionvalue;
    }

    public void setConditionvalue(String conditionvalue) {
        this.conditionvalue = conditionvalue;
    }

    public String getConditioncount() {
        return conditioncount;
    }

    public void setConditioncount(String conditioncount) {
        this.conditioncount = conditioncount;
    }

    public String getNotifications() {
        return notifications;
    }

    public void setNotifications(String notifications) {
        this.notifications = notifications;
    }
}
