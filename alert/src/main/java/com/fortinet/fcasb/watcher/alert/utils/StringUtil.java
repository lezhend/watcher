package com.fortinet.fcasb.watcher.alert.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zliu on 17/3/3.
 */
public class StringUtil {

    private final static SimpleDateFormat STATISTIC_LOG_FORMAT_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat STATISTIC_TABLE_FORTMAT_DATE = new SimpleDateFormat("yyyyMMdd");
    private final static SimpleDateFormat STATISTIC_TABLE_FORTMAT_TIME = new SimpleDateFormat("HH:mm");

    public static String getTableDate(Date date){
        return STATISTIC_TABLE_FORTMAT_DATE.format(date);
    }
    public static String getStatisticsTime(Date date){
        return STATISTIC_TABLE_FORTMAT_TIME.format(date);
    }

    public static String getLogTimestamp(){
        return STATISTIC_LOG_FORMAT_DATETIME.format(new Date());
    }
}
