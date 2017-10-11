package com.fortinet.fcasb.watcher.alert.dao;

import com.fortinet.fcasb.watcher.alert.domain.MonitorMetric;
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
import java.util.List;

/**
 * Created by zliu on 17/3/3.
 private String name;          //define  name , it is unique
 private String uri;          //
 private String type;         //es,logstash,kafka,kibana, server

 private String createTime;
 private String updateTime;
 */
@Component
public class MonitorMetricDao {
    @Autowired
    private InitDatabase initDatabase;
    public void insert(MonitorMetric metric) throws Exception {
        metric.setCreateTime(StringUtil.getLogTimestamp());
        metric.setUpdateTime(metric.getCreateTime());
        int result  = findCount(metric.getName());
        if(result>0){
            throw  new Exception("This record already exists");
        }
        String sql = "INSERT INTO {0} (name,type,uri,createtime,updatetime)" +
                " VALUES (\"{1}\",\"{2}\",\"{3}\",\"{4}\",\"{5}\")";

        sql = MessageFormat.format(sql,TablesEnum.MONITOR_METRIC.getTablename(),
                metric.getName(),
                metric.getType()==null?"":metric.getType().name(),
                metric.getUri()==null?"":metric.getUri(),
                metric.getCreateTime()==null?"":metric.getCreateTime(),
                metric.getUpdateTime()==null?"":metric.getUpdateTime());
        DaoUtil.execute(initDatabase.getConnect(),sql);
    }

    public void update(MonitorMetric metric){
        String sql = "UPDATE \"{0}\" SET type=\"{1}\",uri=\"{2}\",updatetime=\"{3}\" " +
                "WHERE name=\"{4}\"";
        metric.setUpdateTime(StringUtil.getLogTimestamp());

        sql = MessageFormat.format(sql,TablesEnum.MONITOR_METRIC.getTablename(),
                metric.getType()==null?"":metric.getType().name(),
                metric.getUri()==null?"":metric.getUri(),
                metric.getUpdateTime()==null?"":metric.getUpdateTime(),
                metric.getName());
        DaoUtil.execute(initDatabase.getConnect(),sql);
    }

    public MonitorMetric get(String name){
        String sql = "SELECT * FROM {0} WHERE name = \"{1}\"";
        sql = MessageFormat.format(sql, TablesEnum.MONITOR_METRIC.getTablename(), name);
        List<MonitorMetric> result = find(sql);
        if(result.size()>0){
            return result.get(0);
        }
        return null;
    }
    public List<MonitorMetric> findByType(MonitorTypeEnum typeEnum){
        String sql = "SELECT * FROM "+TablesEnum.MONITOR_METRIC.getTablename() +" where type=\""+typeEnum.name()+"\"";
        return find(sql);
    }
    public List<MonitorMetric> find(){
        String sql = "SELECT * FROM "+TablesEnum.MONITOR_METRIC.getTablename();
        return find(sql);
    }

    public List<MonitorMetric> find(String sql){
        Statement statement = null;
        ResultSet rs = null;
        List<MonitorMetric> metrics = new ArrayList<>();
        try {
            statement = initDatabase.getConnect().createStatement();
            rs = statement.executeQuery(sql);
            while ( rs.next() ) {
                metrics.add(trans(rs));
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
        return metrics;
    }
    public int findCount(String name){
        String sql = "SELECT count(0) as nums FROM {0} WHERE name = \"{1}\"";
        sql = MessageFormat.format(sql, TablesEnum.MONITOR_METRIC.getTablename(), name);
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


    private MonitorMetric trans(ResultSet rs) throws SQLException {
        MonitorMetric metric = new MonitorMetric();
        metric.setName(rs.getString("name")==null?"":rs.getString("name"));
        metric.setType(rs.getString("type")==null?null:MonitorTypeEnum.valueOf(rs.getString("type")));
        metric.setUri(rs.getString("uri")==null?"":rs.getString("uri"));
        metric.setUpdateTime(rs.getString("createtime") == null ? "" : rs.getString("createtime"));
        metric.setCreateTime(rs.getString("updatetime") == null ? "" : rs.getString("updatetime"));
        return metric;
    }


}
