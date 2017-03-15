package com.fortinet.fcasb.watcher.alert.controller;

import com.alibaba.fastjson.JSONObject;
import com.fortinet.fcasb.watcher.alert.domain.Alert;
import com.fortinet.fcasb.watcher.alert.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * Created by zliu on 17/3/3.
 */
@RestController
@RequestMapping("/alert")
public class AlertController {
    @Autowired
    private AlertService alertService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getName(@RequestParam("name") String name){
        return JSONObject.toJSONString(alertService.get(name));
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    public String save(@PathParam("name") String name,@RequestBody Alert alert){
        return JSONObject.toJSONString(alertService.save(alert));
    }
    @RequestMapping(value = "/{name}", method = RequestMethod.POST)
    public String update(@PathParam("name") String name,@RequestBody Alert alert){
        return JSONObject.toJSONString(alertService.update(alert));
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    public String update(@PathParam("name") String name){
        return JSONObject.toJSONString(alertService.delete(name));
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(){
        return JSONObject.toJSONString(alertService.find());
    }
    @RequestMapping(value = "/list/logs", method = RequestMethod.GET)
    public String listLogs(){
        return JSONObject.toJSONString(alertService.findLogs());
    }

    @RequestMapping(value = "/list/logs/{name}", method = RequestMethod.GET)
    public String listLogs(@PathParam("name")String name){
        return JSONObject.toJSONString(alertService.findLogsByName(name));
    }
}
