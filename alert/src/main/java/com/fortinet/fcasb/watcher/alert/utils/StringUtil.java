package com.fortinet.fcasb.watcher.alert.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public static String getLogTimestamp(Date date){
        return STATISTIC_LOG_FORMAT_DATETIME.format(date);
    }

    public static Date getCurrentWholeMinTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }
    public static Date getCurrentWholeDayTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }
}
