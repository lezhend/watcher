package com.fortinet.fcasb.watcher.alert.domain;

import java.io.Serializable;

/**
 * Created by zliu on 17/3/3.
 */
public class Alert implements Serializable{

    private static final long serialVersionUID = -5173880754763933700L;

    private String index;         //index name
    private String name;          //alertName
    private String filter;        //search condition
    private String field;         //get field
    private String fvalue;        //field_value
    private String regex;         //如果有值，直接匹配获取，根据正则提取一个值
    private String value;         //after regex match to value
    private String count;         //search count
    private String conditionvalue;      //alert condition >value
    private String conditioncount;      //alert condition >count
    private String thresholdvalue;//
    private String thresholdcount;//

    private String createtime;
    private String updatetime;
    private String notifications; //通知

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

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getFvalue() {
        return fvalue;
    }

    public void setFvalue(String fvalue) {
        this.fvalue = fvalue;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
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

    public String getThresholdvalue() {
        return thresholdvalue;
    }

    public void setThresholdvalue(String thresholdvalue) {
        this.thresholdvalue = thresholdvalue;
    }

    public String getThresholdcount() {
        return thresholdcount;
    }

    public void setThresholdcount(String thresholdcount) {
        this.thresholdcount = thresholdcount;
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

    enum ALERT_CONDITION{
        LT("lt"),
        EQ("eq"),
        GT("gt"),
        CONTAINS("contains");
        private String value;

        ALERT_CONDITION(String value){
            this.value = value;
        }
    }

}
