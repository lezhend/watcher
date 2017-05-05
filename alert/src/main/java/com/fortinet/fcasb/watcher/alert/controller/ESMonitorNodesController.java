package com.fortinet.fcasb.watcher.alert.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/esmonitor/nodes")
public class ESMonitorNodesController {

    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    public String stats() {
        return "";
    }

}
