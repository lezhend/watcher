package com.fortinet.fcasb.watcher.alert.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fortinet.fcasb.watcher.alert.domain.Alert;
import com.fortinet.fcasb.watcher.alert.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zliu on 17/3/3.
 */
@RestController
@RequestMapping("/alert")
public class AlertController {
    @Autowired
    private AlertService alertService;

    @RequestMapping(value = "/get/{name}", method = RequestMethod.GET)
    public String getName(@PathVariable("name") String name){
        return JSONObject.toJSONString(alertService.get(name));
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    public String save(@PathVariable("name") String name,@RequestBody String alert){
        return JSONObject.toJSONString(alertService.save(JSON.parseObject(alert,Alert.class)));
    }
    @RequestMapping(value = "/{name}", method = RequestMethod.POST)
    public String update(@PathVariable("name") String name,@RequestBody String alert){
        return JSONObject.toJSONString(alertService.update(JSON.parseObject(alert,Alert.class)));
    }
    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public String update(){
        return JSONObject.toJSONString(alertService.refreshAlertList());
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    public String update(@PathVariable("name") String name){
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
    public String listLogs(@PathVariable("name")String name){
        return JSONObject.toJSONString(alertService.findLogsByName(name));
    }
}
