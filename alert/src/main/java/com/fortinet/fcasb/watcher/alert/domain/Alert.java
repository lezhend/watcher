package com.fortinet.fcasb.watcher.alert.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zliu on 17/3/3.
 */
public class Alert implements Serializable{
    private static final long serialVersionUID = -4879308807404260222L;
    private String host;         //host name
    private String port;         //port name
    private String index;         //index name
    private String name;          //alertName
    private Map<String,String> filter;        //search condition   field,key
    private String searchkey;        //search condition
    private String field;         //get field value

    private String cvalue;      //alert gt lt le ge eq
    private String conditionvalue;      //alert condition >field value
    private String ccount;      //gt lt le ge eq
    private String conditioncount;      //alert condition >count

    private String createtime;
    private String updatetime;
    private String notifications; //通知 , 分隔
    private String emailtitle; //
    private String emailtemplate; //

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

    public void setFilter(Map<String, String> filter) {
        this.filter = filter;
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

    public String getSearchkey() {
        return searchkey;
    }

    public void setSearchkey(String searchkey) {
        this.searchkey = searchkey;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Map<String, String> getFilter() {
        return filter;
    }

    public void setFilter(HashMap<String, String> filter) {
        this.filter = filter;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
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


    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getNotifications() {
        return notifications;
    }

    public void setNotifications(String notifications) {
        this.notifications = notifications;
    }


}
