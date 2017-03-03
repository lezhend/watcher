package com.fortinet.fcasb.watcher.monitor.init;

import com.fortinet.fcasb.watcher.monitor.dao.StatisticsDao;
import com.fortinet.fcasb.watcher.monitor.enums.TablesEnum;
import com.fortinet.fcasb.watcher.monitor.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

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

    private final String CREATE_TABLE_STATISTICS_SQL = "" +
            "CREATE TABLE IF NOT EXISTS %s "+
            " ( type TEXT  NOT NULL, " +
            " metrics TEXT NOT NULL, " +
            " time TEXT  DEFAULT CURRENT_TIME, " +
            " value TEXT );";


    private static Connection connect = null;

    @PostConstruct
    private void init(){
        initConnect();
        if(connect==null){
            LOGGER.error("init database resource error!!!");
            throw new RuntimeException("init database resource error!!!");
        }

        createStatisticsTables(new Date());
        createStatisticsTables(new Date(new Date().getTime()+24*60*60*1000));
        createStatisticsTables(new Date(new Date().getTime()+2*24*60*60*1000));
    }

    private void initConnect() {
        if(connect==null) {
            try {
                Class.forName("org.sqlite.JDBC");
                connect = DriverManager.getConnection("jdbc:sqlite:" + dbPath + "/monitor.db");
            }catch (Exception ex){
                ex.printStackTrace();
                LOGGER.error(ex.toString());
            }
        }
    }


    public static Connection getConnect(){
        if(connect==null){
            throw new RuntimeException("connnect is null");
        }
        return connect;
    }

    public void createStatisticsTables(Date date){
        String dateStr = StringUtil.getTableDate(date);
        if(connect!=null){
            Statement stmt = null;
            try {
                stmt = connect.createStatement();
                stmt.executeUpdate(String.format(CREATE_TABLE_STATISTICS_SQL, StatisticsDao.transStatisticsTableName(date)));
            } catch (SQLException e) {
                e.printStackTrace();
                LOGGER.error("create {}{} failed {}",TablesEnum.STATISTICS.getTablename(),dateStr,e.toString());

            } finally {
                if(stmt!=null){
                    try {
                        stmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        LOGGER.error(e.toString());
                        LOGGER.error("create {}{} failed {}",TablesEnum.STATISTICS.getTablename(),dateStr,e.toString());
                    }
                }
            }

        }

    }
}
