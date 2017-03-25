package com.fortinet.fcasb.watcher.alert.init;

import com.fortinet.fcasb.watcher.alert.enums.TablesEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
            " indexName TEXT  NOT NULL, " +
            " name TEXT NOT NULL, " +
            " searchkey TEXT , " +
            " filter TEXT , " +
            " field TEXT , " +
            " conditioncount TEXT , " +
            " conditionvalue TEXT , " +
            " createtime TEXT , " +
            " updatetime TEXT , " +
            " notifications TEXT);";

    private final String CREATE_TABLE_ALERT_LOG_SQL = "" +
            "CREATE TABLE IF NOT EXISTS  "+ TablesEnum.ALERT_LOG.getTablename()+
            " (" +
            " name TEXT NOT NULL, " +
            " content TEXT , " +
            " createtime TEXT , " +
            " notifications TEXT);";


    private static Connection connect = null;

    @PostConstruct
    private void init(){
        initConnect();
        if(connect==null){
            LOGGER.error("init database resource error!!!");
            throw new RuntimeException("init database resource error!!!");
        }

        createAlertTables();
        createAlertLogTables();
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

    public void createAlertTables(){
        if(connect!=null){
            Statement stmt = null;
            try {
                stmt = connect.createStatement();
                stmt.executeUpdate(CREATE_TABLE_ALERT_SQL);
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
    public void createAlertLogTables(){
        if(connect!=null){
            Statement stmt = null;
            try {
                stmt = connect.createStatement();
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
