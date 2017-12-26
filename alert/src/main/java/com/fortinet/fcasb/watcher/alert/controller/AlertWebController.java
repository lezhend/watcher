package com.fortinet.fcasb.watcher.alert.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zliu on 17/5/31.
 */
@Controller
@RequestMapping("/alert")
public class AlertWebController {


    @Value("${es.server.hosts}")
    private String[] hosts;

    @Value("${es.server.ports}")
    private String[] ports;

    @GetMapping("/index.html")
    public String index(Map<String, Object> model) {
        model.put("time", new Date());
        return "alert/index";
    }

    @GetMapping("/info.html")
    public String info(Map<String, Object> model) {
        List<String> hostList= new ArrayList();
        for(int i =0;i<hosts.length;i++){
            hostList.add(hosts[i]+":"+ports[i]);
        }
        model.put("hosts", hostList);
        return "alert/info";
    }
    @GetMapping("/logs.html")
    public String logs(Map<String, Object> model) {
        return "alert/logs";
    }
    @GetMapping("/create.html")
    public String create(Map<String, Object> model) {
        List<String> hostList= new ArrayList();
        for(int i =0;i<hosts.length;i++){
            hostList.add(hosts[i]+":"+ports[i]);
        }
        model.put("hosts", hostList);
        return "alert/create";
    }
}
