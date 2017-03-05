package com.fortinet.fcasb.watcher.monitor.service;

import com.fortinet.fcasb.watcher.monitor.enums.OSTypeEnum;
import com.fortinet.fcasb.watcher.monitor.utils.SystemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by zliu on 17/3/4.
 */
@Service
public class SystemService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemService.class);

    @Value("${monitor.system.os}")
    private String osType;

    public String getUseCPUInfo(){
        if(OSTypeEnum.LINUX.toString().equalsIgnoreCase(osType)){
            SystemUtil.linuxCmd("sh","-c","cat /pro/cpuinfo","|","grep use");

        } else if(OSTypeEnum.WINDOWS.toString().equalsIgnoreCase(osType)) {
            //todo
            LOGGER.error("don't support this os {}",osType);

        }else if((OSTypeEnum.MAC_OS.toString().equalsIgnoreCase(osType))) {
            return SystemUtil.linuxCmd("cat","/pro/cpuinfo","|","grep use");

        } else {
            //todo
            LOGGER.error("don't support this os {}",osType);
        }
        return null;
    }
    public String getUseMEMInfo(){
        if(OSTypeEnum.LINUX.toString().equalsIgnoreCase(osType)){
            String result = SystemUtil.linuxCmd("sh", "-c", "free|grep Mem|awk '{pring $2\" \"$3}'");

        } else if(OSTypeEnum.WINDOWS.toString().equalsIgnoreCase(osType)) {
            //todo
            LOGGER.error("don't support this os {}",osType);

        }else if((OSTypeEnum.MAC_OS.toString().equalsIgnoreCase(osType))) {
            return SystemUtil.linuxCmd("sh","-c","top -l 1 |grep 'PhysMem'|awk '{print $2}'");
        } else {
            //todo
            LOGGER.error("don't support this os {}",osType);
        }
        return null;
    }
    public String getRuntime(){
        if(OSTypeEnum.LINUX.toString().equalsIgnoreCase(osType)){


        } else if(OSTypeEnum.WINDOWS.toString().equalsIgnoreCase(osType)) {
            //todo
            LOGGER.error("don't support this os {}",osType);

        }else if((OSTypeEnum.MAC_OS.toString().equalsIgnoreCase(osType))) {

        } else {
            //todo
            LOGGER.error("don't support this os {}",osType);
        }
        return null;
    }

    public String getProgressUseCPU(String progress){
        if(OSTypeEnum.LINUX.toString().equalsIgnoreCase(osType)
                || OSTypeEnum.MAC_OS.toString().equalsIgnoreCase(osType)){
            String[] results = getLinuxProgressInfo(progress);
            if(results==null){
                return null;
            }
             return results[0];

        } else if(OSTypeEnum.WINDOWS.toString().equalsIgnoreCase(osType)) {
            //todo
            LOGGER.error("don't support this os {}",osType);

        } else {
            //todo
            LOGGER.error("don't support this os {}",osType);
        }
        return null;
    }

    public String getProgressUseMEM(String progress){

        if(OSTypeEnum.LINUX.toString().equalsIgnoreCase(osType)
                || OSTypeEnum.MAC_OS.toString().equalsIgnoreCase(osType)){
            String[] results = getLinuxProgressInfo(progress);
            if(results==null){
                return null;
            }
            return results[1];


        } else if(OSTypeEnum.WINDOWS.toString().equalsIgnoreCase(osType)) {
            //todo
            LOGGER.error("don't support this os {}",osType);


        } else {
            //todo
            LOGGER.error("don't support this os {}",osType);
        }
        return null;
    }
    public String getProgressRuntime(String progress){
        if(OSTypeEnum.LINUX.toString().equalsIgnoreCase(osType)
                || OSTypeEnum.MAC_OS.toString().equalsIgnoreCase(osType)){
            String[] results = getLinuxProgressInfo(progress);
            if(results==null){
                return null;
            }
            return results[2];

        } else if(OSTypeEnum.WINDOWS.toString().equalsIgnoreCase(osType)) {
            //todo
            LOGGER.error("don't support this os {}",osType);

        } else {
            //todo
            LOGGER.error("don't support this os {}",osType);
        }
        return null;
    }
    public Boolean progressIsRun(String progress){
        if(OSTypeEnum.LINUX.toString().equalsIgnoreCase(osType)
                || OSTypeEnum.MAC_OS.toString().equalsIgnoreCase(osType)){
            String[] results = getLinuxProgressInfo(progress);
            if(results==null){
                return false;
            }
            return true;

        } else if(OSTypeEnum.WINDOWS.toString().equalsIgnoreCase(osType)) {
            //todo
            LOGGER.error("don't support this os {}",osType);

        } else {
            //todo
            LOGGER.error("don't support this os {}",osType);
        }
        return false;
    }

    private String[] getLinuxProgressInfo(String progress){
        String firstChar = progress.substring(0, 1);
        progress = progress.replaceFirst(firstChar, "[" +firstChar+"]");
        String cmd = "ps aux|grep "+progress+"|awk '{print $3 \" \" $4 \" \" $10}'";
        String result = SystemUtil.linuxCmd("sh","-c",cmd);
        if(result==null){
            return null;
        }
        String[] results = result.split(" ");
        return results;
    }
}
