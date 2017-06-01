package com.fortinet.fcasb.watcher.alert.dao;

import com.fortinet.fcasb.watcher.alert.domain.AlertLog;
import com.fortinet.fcasb.watcher.alert.enums.TablesEnum;
import com.fortinet.fcasb.watcher.alert.init.InitDatabase;
import com.fortinet.fcasb.watcher.alert.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zliu on 17/3/3.
 private String index;         //index name
 private String name;          //alertName
 private String filter;        //search condition
 private String field;         //get field
 private String fvalue;        //field_value
 private String regex;         //如果有值，直接匹配获取，根据正则提取一个值
 private String value;         //after regex match to value
 private String count;         //search count
 private String condition;      //alert condition >count,>value
 private String thresholdvalue;//
 private String thresholdcount;//
 private String createtime;
 private String updatetime;
 private String notifications; //通知
 */
@Component
public class AlertLogDao {
    @Autowired
    private InitDatabase initDatabase;
    public void insert(AlertLog alertLog){
        alertLog.setCreatetime(StringUtil.getTableDate(new Date()));
        String sql = "INSERT INTO {0} (name,content,createtime,notifications)" +
                " VALUES (\"{1}\",\"{2}\",\"{3}\",\"{4}\"" +
                ")";
        sql = MessageFormat.format(sql,TablesEnum.ALERT_LOG.getTablename(),
                alertLog.getName(),alertLog.getContent(),alertLog.getCreatetime(),alertLog.getNotifications());
        Statement statement = null;
        try {
            statement = initDatabase.getConnect().createStatement();
            statement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if(statement!=null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public List<AlertLog> get(String name){
        String sql = "SELECT * FROM {0} WHERE name = \"{1}\" order by createtime ASC limit 100";
        sql = MessageFormat.format(sql, TablesEnum.ALERT_LOG.getTablename(), name);
        Statement statement = null;
        ResultSet rs = null;
        List<AlertLog> alertLogList = new ArrayList<>();
        try {
            statement = initDatabase.getConnect().createStatement();
            rs = statement.executeQuery(sql);
            while ( rs.next() ) {
                AlertLog alert = new AlertLog();
                alert.setName(rs.getString("name"));
                alert.setCreatetime(rs.getString("createtime"));
                alert.setContent(rs.getString("content"));
                alert.setNotifications(rs.getString("notifications"));
                alertLogList.add(alert);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(statement!=null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return alertLogList;
    }

    public List<AlertLog> find(){
        String sql = "SELECT * FROM {0} order by createtime ASC limit 100";
        sql = MessageFormat.format(sql, TablesEnum.ALERT_LOG.getTablename());
        Statement statement = null;
        ResultSet rs = null;
        List<AlertLog> alertLogList = new ArrayList<>();
        try {
            statement = initDatabase.getConnect().createStatement();
            rs = statement.executeQuery(sql);
            while ( rs.next() ) {
                AlertLog alert = new AlertLog();
                alert.setName(rs.getString("name"));
                alert.setCreatetime(rs.getString("createtime"));
                alert.setContent(rs.getString("content"));
                alert.setNotifications(rs.getString("notifications"));
                alertLogList.add(alert);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(statement!=null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return alertLogList;
    }

    public boolean delete(String name){
        String sql = "DELETE FROM {0} WHERE name = \"{1}\"";
        sql = MessageFormat.format(sql, TablesEnum.ALERT.getTablename(), name);
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = initDatabase.getConnect().createStatement();
            return statement.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(statement!=null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }



}
