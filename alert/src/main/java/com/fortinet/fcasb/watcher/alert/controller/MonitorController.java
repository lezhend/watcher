package com.fortinet.fcasb.watcher.alert.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fortinet.fcasb.watcher.alert.domain.Monitor;
import com.fortinet.fcasb.watcher.alert.domain.MonitorMetric;
import com.fortinet.fcasb.watcher.alert.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zliu on 17/3/3.
 */
@RestController
@RequestMapping("/monitor")
public class MonitorController {
    @Autowired
    private MonitorService monitorService;

    @RequestMapping(value = "/get/{name}", method = RequestMethod.GET)
    public String getName(@PathVariable("name") String name){
        return JSONObject.toJSONString(monitorService.get(name));
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    public String save(@PathVariable("name") String name,@RequestBody String monitor){
        return JSONObject.toJSONString(monitorService.save(JSON.parseObject(monitor,Monitor.class)));
    }
    @RequestMapping(value = "/{name}", method = RequestMethod.POST)
    public String update(@PathVariable("name") String name,@RequestBody String monitor){
        return JSONObject.toJSONString(monitorService.update(JSON.parseObject(monitor,Monitor.class)));
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("name") String name){
        return JSONObject.toJSONString(monitorService.delete(name));
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(){
        return JSONObject.toJSONString(monitorService.find());
    }


    @RequestMapping(value = "/metric/get/{name}", method = RequestMethod.GET)
    public String getMetricName(@PathVariable("name") String name){
        return JSONObject.toJSONString(monitorService.getMetric(name));
    }

    @RequestMapping(value = "/metric/{name}", method = RequestMethod.PUT)
    public String saveMetric(@PathVariable("name") String name,@RequestBody String metric){
        return JSONObject.toJSONString(monitorService.saveMetric(JSON.parseObject(metric,MonitorMetric.class)));
    }
    @RequestMapping(value = "/metric/{name}", method = RequestMethod.POST)
    public String updateMetric(@PathVariable("name") String name,@RequestBody String metric){
        return JSONObject.toJSONString(monitorService.updateMetric(JSON.parseObject(metric,MonitorMetric.class)));
    }

    @RequestMapping(value = "/metric/{name}", method = RequestMethod.DELETE)
    public String deleteMetric(@PathVariable("name") String name){
        return JSONObject.toJSONString(monitorService.deleteMetric(name));
    }

    @RequestMapping(value = "/metric/list", method = RequestMethod.GET)
    public String listMetric(){
        return JSONObject.toJSONString(monitorService.findMetric());
    }
}
