package com.fortinet.fcasb.watcher.alert.controller;

import com.alibaba.fastjson.JSONObject;
import com.fortinet.fcasb.watcher.alert.service.AlertService;
import com.fortinet.fcasb.watcher.alert.utils.StringUtil;
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
public class AlertController {
    @Autowired
    private AlertService alertService;

    @RequestMapping(value = "/statistic", method = RequestMethod.GET)
    public String statistics(@RequestParam("startTime") String startTime,@RequestParam(value = "endTime",defaultValue = "")String endTime){
        if(StringUtils.isEmpty(endTime)){
            endTime = StringUtil.getStatisticsTime(new Date());
        }
        return JSONObject.toJSONString(alertService.find(startTime,endTime));
    }

}
