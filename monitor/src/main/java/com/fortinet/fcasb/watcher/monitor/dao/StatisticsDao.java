package com.fortinet.fcasb.watcher.monitor.dao;

import com.fortinet.fcasb.watcher.monitor.domain.Statistics;
import com.fortinet.fcasb.watcher.monitor.enums.TablesEnum;
import com.fortinet.fcasb.watcher.monitor.init.InitDatabase;
import com.fortinet.fcasb.watcher.monitor.utils.StringUtil;
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
 */
@Component
public class StatisticsDao {
    public void insert(Statistics statistics){
        String sql = "INSERT INTO {0} (timestamp,name,hostname,type,metrics,time,filter,value) VALUES (\"{1}\",\"{2}\",\"{3}\",\"{4}\",\"{5}\",\"{6}\",\"{7}\",\"{8}\")";
        Date date = new Date();
        sql = MessageFormat.format(sql,transStatisticsTableName(date),
                statistics.getTimestamp(),statistics.getName(),statistics.getHostname(),
                statistics.getType(), statistics.getMetrics(), statistics.getTime(), statistics.getFilter(),statistics.getValue());
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

    public List<Statistics> find(Date date,String startTime,String endTime){
        String sql = "SELECT * FROM {0} WHERE time >= \"{1}\" AND time <= \"{2}\"";
        sql = MessageFormat.format(sql, transStatisticsTableName(date), startTime, endTime);
        Statement statement = null;
        ResultSet rs = null;
        List<Statistics> statisticses = new ArrayList<>();
        try {
            statement = InitDatabase.getConnect().createStatement();
            rs = statement.executeQuery(sql);
            while ( rs.next() ) {
                Statistics statistics = new Statistics();
                statistics.setTimestamp(rs.getString("timestamp"));
                statistics.setHostname(rs.getString("hostname"));
                statistics.setName(rs.getString("name"));
                statistics.setMetrics(rs.getString("metrics"));
                statistics.setTime(rs.getString("time"));
                statistics.setType(rs.getString("type"));
                statistics.setValue(rs.getString("value"));
                statistics.setFilter(rs.getString("filter"));
                statisticses.add(statistics);
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
        return statisticses;
    }

    public void dropTable(Date date){
         String sql = "DROP TABLE IF EXISTS "+ transStatisticsTableName(date);
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

    public static String transStatisticsTableName(Date date){
        return TablesEnum.STATISTICS.getTablename()+ StringUtil.getTableDate(date);
    }

}
