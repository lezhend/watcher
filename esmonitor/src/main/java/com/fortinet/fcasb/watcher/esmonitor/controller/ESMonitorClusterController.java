package com.fortinet.fcasb.watcher.esmonitor.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zliu on 17/3/3.
 */
@RestController
@RequestMapping("/esmonitor/cluster")
public class ESMonitorClusterController {

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public String health(){
        return "";
    }

    //get cluster cpu jvm memory
    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    public String stats(){
        return "";
    }
    //get cluster all info
    @RequestMapping(value = "/sate", method = RequestMethod.GET)
    public String sate(){
        return "";
    }
    @RequestMapping(value = "/stats/{nodeName}", method = RequestMethod.GET)
    public String noteSats(@PathVariable("nodeName") String nodeName){
        return "";
    }

}
