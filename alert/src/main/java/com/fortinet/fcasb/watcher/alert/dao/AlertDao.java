package com.fortinet.fcasb.watcher.alert.dao;

import com.fortinet.fcasb.watcher.alert.domain.Alert;
import com.fortinet.fcasb.watcher.alert.enums.TablesEnum;
import com.fortinet.fcasb.watcher.alert.init.InitDatabase;
import com.fortinet.fcasb.watcher.alert.utils.StringUtil;
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
public class AlertDao {
    public void insert(Alert alert){
        alert.setCreatetime(StringUtil.getTableDate(new Date()));
        alert.setUpdatetime(alert.getCreatetime());
        String sql = "INSERT INTO {0} (index,name,filter,field,fvalue,regex,value,count,conditioncount,conditionvalue,thresholdcount,thresholdvalue,createtime,updatetime,notifications)" +
                " VALUES (\"{1}\",\"{2}\",\"{3}\",\"{4}\",\"{5}\",\"{6}\",\"{7}\",\"{8}\",\"{9}\",\"{10}\",\"{11}\",\"{12}\",\"{13}\",\"{14}\",\"{15}\")";
        Date date = new Date();
        sql = MessageFormat.format(sql,TablesEnum.ALERT.getTablename(),
                alert.getIndex(),alert.getName(),alert.getFilter(),
                alert.getField(),alert.getFvalue(),alert.getRegex(),
                alert.getValue(),alert.getCount(),alert.getConditioncount(),alert.getConditionvalue(),
                alert.getThresholdcount(),alert.getThresholdvalue(),alert.getCreatetime(),
                alert.getUpdatetime(),alert.getNotifications());
        Statement statement = null;
        try {
            statement = InitDatabase.getConnect().createStatement();
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

    public void update(Alert alert){
        alert.setUpdatetime(StringUtil.getTableDate(new Date()));
        String sql = "UPDATE {0} SET index={1},filter={2},field={3},fvalue={4},regex={5},value={6},count={7}," +
                "conditioncount={8},conditionvalue={9},thresholdcount={10},thresholdvalue={11},updatetime={12},notifications={13} " +
                "WHERE name={14}";
        Date date = new Date();
        sql = MessageFormat.format(sql,TablesEnum.ALERT.getTablename(),
                alert.getIndex(),alert.getFilter(),
                alert.getField(),alert.getFvalue(),alert.getRegex(),
                alert.getValue(),alert.getCount(),alert.getConditioncount(),alert.getConditionvalue(),
                alert.getThresholdcount(),alert.getThresholdvalue(),
                alert.getUpdatetime(),alert.getNotifications(),alert.getName());
        Statement statement = null;
        try {
            statement = InitDatabase.getConnect().createStatement();
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

    public Alert get(String name){
        String sql = "SELECT * FROM {0} WHERE name = \"{1}\"";
        sql = MessageFormat.format(sql, TablesEnum.ALERT.getTablename(), name);
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = InitDatabase.getConnect().createStatement();
            rs = statement.executeQuery(sql);
            while ( rs.next() ) {
                Alert alert = new Alert();
                alert.setIndex(rs.getString("index"));
                alert.setName(rs.getString("name"));
                alert.setFilter(rs.getString("filter"));
                alert.setField(rs.getString("field"));
                alert.setFvalue(rs.getString("fvalue"));
                alert.setRegex(rs.getString("regex"));
                alert.setValue(rs.getString("value"));
                alert.setCount(rs.getString("count"));
                alert.setConditioncount(rs.getString("conditioncount"));
                alert.setConditionvalue(rs.getString("conditionvalue"));
                alert.setThresholdcount(rs.getString("thresholdcount"));
                alert.setThresholdvalue(rs.getString("thresholdvalue"));
                alert.setCreatetime(rs.getString("createtime"));
                alert.setUpdatetime(rs.getString("updatetime"));
                alert.setNotifications(rs.getString("notifications"));
                return alert;
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
        return null;
    }

    public List<Alert> find(){
        String sql = "SELECT * FROM "+TablesEnum.ALERT.getTablename();
        Statement statement = null;
        ResultSet rs = null;
        List<Alert> alertList = new ArrayList<>();
        try {
            statement = InitDatabase.getConnect().createStatement();
            rs = statement.executeQuery(sql);
            while ( rs.next() ) {
                Alert alert = new Alert();
                alert.setIndex(rs.getString("index"));
                alert.setName(rs.getString("name"));
                alert.setFilter(rs.getString("filter"));
                alert.setField(rs.getString("field"));
                alert.setFvalue(rs.getString("fvalue"));
                alert.setRegex(rs.getString("regex"));
                alert.setValue(rs.getString("value"));
                alert.setCount(rs.getString("count"));
                alert.setConditioncount(rs.getString("conditioncount"));
                alert.setConditionvalue(rs.getString("conditionvalue"));
                alert.setThresholdcount(rs.getString("thresholdcount"));
                alert.setThresholdvalue(rs.getString("thresholdvalue"));
                alert.setCreatetime(rs.getString("createtime"));
                alert.setUpdatetime(rs.getString("updatetime"));
                alert.setNotifications(rs.getString("notifications"));
                alertList.add(alert);
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
        return alertList;
    }
    public int findCount(String name){
        String sql = "SELECT count(0) as nums FROM {0} WHERE name = \"{1}\"";
        sql = MessageFormat.format(sql, TablesEnum.ALERT.getTablename(), name);
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = InitDatabase.getConnect().createStatement();
            rs = statement.executeQuery(sql);
            while ( rs.next() ) {
                return rs.getInt("nums");
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
        return -1;
    }

    public boolean delete(String name){
        String sql = "DELETE FROM {0} WHERE name = \"{1}\"";
        sql = MessageFormat.format(sql, TablesEnum.ALERT.getTablename(), name);
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = InitDatabase.getConnect().createStatement();
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
