package com.fortinet.fcasb.watcher.monitor.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by zliu on 17/3/3.
 */
public class SystemUtil {
    public static String linuxCmd(String... cmd){
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            if(process.waitFor()!=0){
                return null;
            }
            InputStreamReader ir=new InputStreamReader(process.getInputStream());
            BufferedReader input = new BufferedReader(ir);
            return input.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
