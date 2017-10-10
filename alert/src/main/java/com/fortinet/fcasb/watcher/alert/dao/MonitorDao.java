package com.fortinet.fcasb.watcher.alert.dao;

import com.fortinet.fcasb.watcher.alert.domain.Monitor;
import com.fortinet.fcasb.watcher.alert.enums.MonitorTypeEnum;
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
 * private String host;            //host name
 private String port;           //port name
 private String method;         //http,https,ssh, ...

 private String name;          //define monitor server name , it is unique
 private String label;         //define label name

 private String type;         //es,logstash,kafka,kibana, server

 private String createTime;
 private String updateTime;
 */
@Component
public class MonitorDao {
    @Autowired
    private InitDatabase initDatabase;
    public void insert(Monitor monitor) throws Exception {
        monitor.setCreateTime(StringUtil.getTableDate(new Date()));
        monitor.setUpdateTime(monitor.getCreateTime());
        Monitor result  = get(monitor.getName());
        if(result!=null){
            throw  new Exception("This record already exists");
        }
        String sql = "INSERT INTO {0} (host,port,method,name,type,label,createtime,updatetime)" +
                " VALUES (\"{1}\",\"{2}\",\"{3}\",\"{4}\",\"{5}\",\"{6}\",\"{7}\",\"{8}\")";

        sql = MessageFormat.format(sql,TablesEnum.MONITOR.getTablename(),
                monitor.getHost(),
                monitor.getPort(),
                monitor.getMethod(),
                monitor.getName(),
                monitor.getType()==null?"":monitor.getType(),
                monitor.getLabel()==null?"":monitor.getLabel(),
                monitor.getCreateTime()==null?"":monitor.getCreateTime(),
                monitor.getUpdateTime()==null?"":monitor.getUpdateTime());
        DaoUtil.execute(initDatabase.getConnect(),sql);
    }

    public void update(Monitor monitor){
        String sql = "UPDATE \"{0}\" SET host=\"{1}\",port=\"{2}\",method=\"{3}\",type=\"{4}\"," +
                "label=\"{5}\",updatetime=\"{6}\" " +
                "WHERE name=\"{7}\"";
        monitor.setUpdateTime(StringUtil.getTableDate(new Date()));

        sql = MessageFormat.format(sql,TablesEnum.MONITOR.getTablename(),
                monitor.getHost(),
                monitor.getPort(),
                monitor.getMethod(),
                monitor.getType()==null?"":monitor.getType(),
                monitor.getLabel()==null?"":monitor.getLabel(),
                monitor.getUpdateTime()==null?"":monitor.getUpdateTime(),
                monitor.getName());
        DaoUtil.execute(initDatabase.getConnect(),sql);
    }

    public Monitor get(String name){
        String sql = "SELECT * FROM {0} WHERE name = \"{1}\"";
        sql = MessageFormat.format(sql, TablesEnum.MONITOR.getTablename(), name);
        List<Monitor> result = find(sql);
        if(result.size()>0){
            return result.get(0);
        }
        return null;
    }
    public List<Monitor> findByType(MonitorTypeEnum typeEnum){
        String sql = "SELECT * FROM "+TablesEnum.MONITOR.getTablename() +" where type=\""+typeEnum.name()+"\"";
        return find(sql);
    }
    public List<Monitor> find(){
        String sql = "SELECT * FROM "+TablesEnum.MONITOR.getTablename();
        return find(sql);
    }

    public List<Monitor> find(String sql){
        Statement statement = null;
        ResultSet rs = null;
        List<Monitor> monitors = new ArrayList<>();
        try {
            statement = initDatabase.getConnect().createStatement();
            rs = statement.executeQuery(sql);
            while ( rs.next() ) {
                monitors.add(trans(rs));
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
        return monitors;
    }
    public int findCount(String name){
        String sql = "SELECT count(0) as nums FROM {0} WHERE name = \"{1}\"";
        sql = MessageFormat.format(sql, TablesEnum.MONITOR.getTablename(), name);
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
        return 0;
    }

    public boolean delete(String name){
        String sql = "DELETE FROM {0} WHERE name = \"{1}\"";
        sql = MessageFormat.format(sql, TablesEnum.MONITOR.getTablename(), name);
        DaoUtil.execute(initDatabase.getConnect(),sql);
        return true;
    }


    private Monitor trans(ResultSet rs) throws SQLException {
        Monitor monitor = new Monitor();
        monitor.setHost(rs.getString("host")==null?"":rs.getString("host"));
        monitor.setPort(rs.getString("port")==null?"":rs.getString("port"));
        monitor.setName(rs.getString("name")==null?"":rs.getString("name"));
        monitor.setMethod(rs.getString("method")==null?"":rs.getString("method"));
        monitor.setType(rs.getString("type")==null?"":rs.getString("type"));
        monitor.setLabel(rs.getString("label")==null?"":rs.getString("label"));
        monitor.setUpdateTime(rs.getString("createtime") == null ? "" : rs.getString("createtime"));
        monitor.setCreateTime(rs.getString("updatetime") == null ? "" : rs.getString("updatetime"));
        return monitor;
    }


}
