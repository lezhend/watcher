package com.fortinet.fcasb.watcher.alert.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zliu on 17/3/3.
 */
public class Alert implements Serializable{

    private static final long serialVersionUID = -5173880754763933700L;

    private String index;         //index name
    private String name;          //alertName
    private Map<String,String> filter;        //search condition   field,key
    private String searchkey;        //search condition
    private String field;         //get field value

    private String conditionvalue;      //alert condition >value
    private String conditioncount;      //alert condition >count

    private String createtime;
    private String updatetime;
    private String notifications; //通知

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

    enum ALERT_CONDITION{
        LT("<"),
        EQ(">"),
        GT("="),
        CONTAINS("in");
        private String value;

        ALERT_CONDITION(String value){
            this.value = value;
        }
    }

}
