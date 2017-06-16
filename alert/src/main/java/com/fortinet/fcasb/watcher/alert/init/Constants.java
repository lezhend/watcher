package com.fortinet.fcasb.watcher.alert.init;

/**
 * Created by zliu on 17/3/17.
 */
public interface Constants {
    public interface DEFAULT {
        public final static Integer FIND_RECORD_LIMIT = 20;
        public final static  String EMPTY_STRING = "";

        public final static  String COMMON_CHARSET = "UTF-8";

        public final static  String FILE_SEPARATOR = System.getProperty("file.separator");

        public final static  String LINE_SEPARATOR = System.getProperty("line.separator");
    }

    public interface MAX {
        public final static Integer FIND_RECORD = 1000;
    }


    public final static String GEO_UNKNOWN = "unknown";
    

}
