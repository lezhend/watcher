package com.fortinet.fcasb.watcher.alert.init;

import com.fortinet.fcasb.watcher.alert.dao.MonitorMetricDao;
import com.fortinet.fcasb.watcher.alert.domain.MonitorMetric;
import com.fortinet.fcasb.watcher.alert.enums.MonitorTypeEnum;
import com.fortinet.fcasb.watcher.alert.enums.TablesEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by zliu on 17/3/3.
 * 1.Init databases tables
 * 2.create tables on everyday
 */
@Service
public class InitDatabase {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitDatabase.class);



    @Value("${config.data.path}")
    private String dbPath;

    @Autowired
    private MonitorMetricDao metricDao;
    /**
     *  private String index;         //index name
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
     private String notifications; //通知
     */
    private final String CREATE_TABLE_ALERT_SQL = "" +
            "CREATE TABLE IF NOT EXISTS  "+ TablesEnum.ALERT.getTablename()+
            " (" +
            " host TEXT, " +
            " port TEXT, " +
            " indexName TEXT  NOT NULL, " +
            " name TEXT NOT NULL, " +
            " searchkey TEXT , " +
            " filter TEXT , " +
            " field TEXT , " +
            " ccount TEXT , " +
            " conditioncount TEXT , " +
            " cvalue TEXT , " +
            " conditionvalue TEXT , " +
            " createtime TEXT , " +
            " updatetime TEXT , " +
            " emailtitle TEXT , " +
            " emailtemplate TEXT , " +
            " notifications TEXT);";

    private final String CREATE_TABLE_ALERT_LOG_SQL = "" +
            "CREATE TABLE IF NOT EXISTS  "+ TablesEnum.ALERT_LOG.getTablename()+
            " (" +
            " name TEXT NOT NULL, " +
            " content TEXT , " +
            " createtime TEXT , " +
            " notifications TEXT);";


    private final String CREATE_TABLE_MONITOR_SQL = "" +
            "CREATE TABLE IF NOT EXISTS  "+ TablesEnum.MONITOR.getTablename()+
            " (" +
            " host TEXT NOT NULL, " +
            " port TEXT NOT NULL, " +
            " name TEXT NOT NULL, " +
            " method TEXT NOT NULL, " +
            " label TEXT NOT NULL, " +
            " type TEXT NOT NULL, " +
            " createtime TEXT , " +
            " updatetime TEXT);";

    private final String CREATE_TABLE_MONITOR_METRIC_SQL = "" +
            "CREATE TABLE IF NOT EXISTS  "+ TablesEnum.MONITOR_METRIC.getTablename()+
            " (" +
            " name TEXT NOT NULL, " +
            " type TEXT NOT NULL, " +
            " uri TEXT NOT NULL, " +
            " createtime TEXT , " +
            " updatetime TEXT);";

    private static Connection connect = null;

    @PostConstruct
    private void init(){
        initConnect();
        if(connect==null){
            LOGGER.error("init database resource error!!!");
            throw new RuntimeException("init database resource error!!!");
        }
        createTables();
        defaultData();
    }

    private void defaultData(){
        MonitorMetric metric =new MonitorMetric();
        metric.setUri("/_cluster/health");
        metric.setName("cluster_health");
        metric.setType(MonitorTypeEnum.ES);
        try {
            metricDao.insert(metric);
        } catch (Exception e) {

        }
        metric.setUri("/_cluster/stats");
        metric.setName("cluster_stats");
        try {
            metricDao.insert(metric);
        } catch (Exception e) {

        }
        
//        metrics.put("stats_process","/_node/stats/process") ;
//        metrics.put("stats_jvm","/_node/stats/jvm") ;

        metric.setType(MonitorTypeEnum.LOGSTASH);
        metric.setUri("/_node/stats/process");
        metric.setName("stats_process");
        try {
            metricDao.insert(metric);
        } catch (Exception e) {

        }

        metric.setUri("/_node/stats/stats_jvm");
        metric.setName("stats_jvm");
        try {
            metricDao.insert(metric);
        } catch (Exception e) {

        }


    }

    private void initConnect() {
        if(connect==null) {
            try {
                Class.forName("org.sqlite.JDBC");
                connect = DriverManager.getConnection("jdbc:sqlite:" + dbPath + "/alert.db");
            }catch (Exception ex){
                ex.printStackTrace();
                LOGGER.error(ex.toString());
            }
        }
    }


    public Connection getConnect(){
        if(connect==null){
            throw new RuntimeException("connnect is null");
        }
        return connect;
    }

    public void createTables(){
        if(connect!=null){
            Statement stmt = null;
            try {
                stmt = connect.createStatement();
                stmt.executeUpdate(CREATE_TABLE_ALERT_SQL);
                stmt.executeUpdate(CREATE_TABLE_MONITOR_SQL);
                stmt.executeUpdate(CREATE_TABLE_MONITOR_METRIC_SQL);
                stmt.executeUpdate(CREATE_TABLE_ALERT_LOG_SQL);
            } catch (SQLException e) {
                e.printStackTrace();
                LOGGER.error("create {} failed {}", TablesEnum.ALERT.getTablename(),e.toString());

            } finally {
                if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        LOGGER.error(e.toString());
                        LOGGER.error("create {} failed {}",TablesEnum.ALERT.getTablename(),e.toString());
                    }
                }
            }

        }

    }
}
