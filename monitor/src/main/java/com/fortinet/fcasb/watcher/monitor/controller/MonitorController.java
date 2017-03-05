package com.fortinet.fcasb.watcher.monitor.controller;

import com.alibaba.fastjson.JSONObject;
import com.fortinet.fcasb.watcher.monitor.service.StatisticsService;
import com.fortinet.fcasb.watcher.monitor.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by zliu on 17/3/3.
 */
@RestController
@RequestMapping("/monitor")
public class MonitorController {
    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping(value = "/statistic", method = RequestMethod.GET)
    public String statistics(@RequestParam("startTime") String startTime,@RequestParam(value = "endTime",defaultValue = "")String endTime){
        if(StringUtils.isEmpty(endTime)){
            endTime = StringUtil.getStatisticsTime(new Date());
        }
        return JSONObject.toJSONString(statisticsService.find(startTime,endTime));
    }

}
