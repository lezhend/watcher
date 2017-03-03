package com.fortinet.fcasb.watcher.monitor.controller;

import com.alibaba.fastjson.JSONObject;
import com.fortinet.fcasb.watcher.monitor.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zliu on 17/3/3.
 */
@RestController
@RequestMapping("/monitor")
public class MonitorController {
    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping(value = "/statistic", method = RequestMethod.GET)
    public String statistics(@RequestParam("startTime") String startTime,@RequestParam("endTime")String endTime){
        return JSONObject.toJSONString(statisticsService.find(startTime,endTime));
    }

}
