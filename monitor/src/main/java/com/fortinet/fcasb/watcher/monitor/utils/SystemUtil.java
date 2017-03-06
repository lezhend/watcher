package com.fortinet.fcasb.watcher.monitor.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by zliu on 17/3/3.
 */
public class SystemUtil {
    private static String HOST_NAME= null;

    public static String getHostname(){
        if(HOST_NAME==null) {
            try {
                HOST_NAME = InetAddress.getLocalHost().getHostName();
                return InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            return "unknown";
        }else{
            return HOST_NAME;
        }
    }
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
