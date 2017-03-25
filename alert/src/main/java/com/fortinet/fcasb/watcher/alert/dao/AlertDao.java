package com.fortinet.fcasb.watcher.alert.dao;

import com.alibaba.fastjson.JSON;
import com.fortinet.fcasb.watcher.alert.domain.Alert;
import com.fortinet.fcasb.watcher.alert.enums.TablesEnum;
import com.fortinet.fcasb.watcher.alert.init.InitDatabase;
import com.fortinet.fcasb.watcher.alert.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    @Autowired
    private InitDatabase initDatabase;
    public void insert(Alert alert){
        alert.setCreatetime(StringUtil.getTableDate(new Date()));
        alert.setUpdatetime(alert.getCreatetime());
        String sql = "INSERT INTO {0} (indexName,name,searchkey,filter,field,conditioncount,conditionvalue,createtime,updatetime,notifications)" +
                " VALUES (\"{1}\",\"{2}\",\"{3}\",\"{4}\",\"{5}\",\"{6}\",\"{7}\",\"{8}\",\"{9}\",\"{10}\")";
        String strFilter="";
        if(alert.getFilter()!=null){
            strFilter = URLEncoder.encode(JSON.toJSONString(alert.getFilter()));
        }
        sql = MessageFormat.format(sql,TablesEnum.ALERT.getTablename(),
                alert.getIndex(),
                alert.getName(),
                alert.getSearchkey()==null?"":alert.getSearchkey(),
                strFilter,
                alert.getField()==null?"":alert.getField(),
                alert.getConditioncount()==null?"":alert.getConditioncount(),
                alert.getConditionvalue()==null?"":alert.getConditionvalue(),
                alert.getCreatetime()==null?"":alert.getCreatetime(),
                alert.getUpdatetime()==null?"":alert.getUpdatetime(),
                alert.getNotifications()==null?"":alert.getNotifications());
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

    public void update(Alert alert){
        alert.setUpdatetime(StringUtil.getTableDate(new Date()));
        String sql = "UPDATE \"{0}\" SET indexName=\"{1}\",searchkey=\"{2}\",filter=\"{3}\",field=\"{4}\"," +
                "conditioncount=\"{5}\",conditionvalue=\"{6}\",updatetime=\"{7}\",notifications=\"{8}\" " +
                "WHERE name=\"{9}\"";
        String strFilter="";
        if(alert.getFilter()!=null){
            strFilter = URLEncoder.encode(JSON.toJSONString(alert.getFilter()));
        }
        sql = MessageFormat.format(sql,TablesEnum.ALERT.getTablename(),
                alert.getIndex(),
                alert.getSearchkey()==null?"":alert.getSearchkey(),
                strFilter,
                alert.getField()==null?"":alert.getField(),
                alert.getConditioncount()==null?"":alert.getConditioncount(),
                alert.getConditionvalue()==null?"":alert.getConditionvalue(),
                alert.getUpdatetime()==null?"":alert.getUpdatetime(),
                alert.getNotifications()==null?"":alert.getNotifications(),
                alert.getName());
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

    public Alert get(String name){
        String sql = "SELECT * FROM {0} WHERE name = \"{1}\"";
        sql = MessageFormat.format(sql, TablesEnum.ALERT.getTablename(), name);
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = initDatabase.getConnect().createStatement();
            rs = statement.executeQuery(sql);
            while ( rs.next() ) {
                return trans(rs);
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
            statement = initDatabase.getConnect().createStatement();
            rs = statement.executeQuery(sql);
            while ( rs.next() ) {
                alertList.add(trans(rs));
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
            statement = initDatabase.getConnect().createStatement();
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


    private Alert trans(ResultSet rs) throws SQLException {
        Alert alert = new Alert();
        alert.setIndex(rs.getString("indexName")==null?"":rs.getString("indexName"));
        alert.setName(rs.getString("name")==null?"":rs.getString("name"));
        alert.setSearchkey(rs.getString("searchkey")==null?"":rs.getString("searchkey"));
        String filter = rs.getString("filter")==null?"":rs.getString("filter");
        if(!StringUtils.isEmpty(filter)){
            alert.setFilter(JSON.parseObject(URLDecoder.decode(filter), HashMap.class));
        }
        alert.setField(rs.getString("field")==null?"":rs.getString("field"));
        alert.setConditioncount(rs.getString("conditioncount")==null?"":rs.getString("conditioncount"));
        alert.setConditionvalue(rs.getString("conditionvalue")==null?"":rs.getString("conditionvalue"));
        alert.setCreatetime(rs.getString("createtime")==null?"":rs.getString("createtime"));
        alert.setUpdatetime(rs.getString("updatetime")==null?"":rs.getString("updatetime"));
        alert.setNotifications(rs.getString("notifications")==null?"":rs.getString("notifications"));
        return alert;
    }


}
