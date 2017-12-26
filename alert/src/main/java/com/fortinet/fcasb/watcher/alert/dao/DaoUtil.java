package com.fortinet.fcasb.watcher.alert.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by zliu on 17/10/10.
 */
public class DaoUtil {
    public static void execute(Connection connection,String sql){
        Statement statement = null;
        try {
            statement = connection.createStatement();
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
}
