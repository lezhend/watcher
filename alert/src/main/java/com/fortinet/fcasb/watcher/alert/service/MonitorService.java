package com.fortinet.fcasb.watcher.alert.service;

import com.fortinet.fcasb.watcher.alert.dao.MonitorDao;
import com.fortinet.fcasb.watcher.alert.dao.MonitorMetricDao;
import com.fortinet.fcasb.watcher.alert.domain.Monitor;
import com.fortinet.fcasb.watcher.alert.domain.MonitorMetric;
import com.fortinet.fcasb.watcher.alert.domain.Result;
import com.fortinet.fcasb.watcher.alert.enums.MonitorTypeEnum;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zliu on 17/3/3.
 */
@Service
public class MonitorService {
    public static final Logger LOGGER = LoggerFactory.getLogger(MonitorService.class);


    @Autowired
    private MonitorDao monitorDao;
    @Autowired
    private MonitorMetricDao metricDao;


    public Result<List<Monitor>> find(){
        List<Monitor> monitors = monitorDao.find();
        Result<List<Monitor>> result = new Result<>();
        result.setCode(0);
        result.setMsg("ok");
        result.setData(monitors);
        return result;
    }

    public Result<List<Monitor>> find(MonitorTypeEnum typeEnum){
        List<Monitor> monitors = monitorDao.findByType(typeEnum);
        Result<List<Monitor>> result = new Result<>();
        result.setCode(0);
        result.setMsg("ok");
        result.setData(monitors);
        return result;
    }



    public Result<Monitor> get(String name){
        Result<Monitor> result = new Result<>();
        if(StringUtils.isBlank(name)){
            result.setCode(100);
            result.setMsg("params error");
            return result;
        }
        Monitor monitor = monitorDao.get(name);
        result.setCode(0);
        result.setMsg("ok");
        result.setData(monitor);
        return result;
    }

    public Result<String> save(Monitor monitor){
        Result<String> result = new Result<>();
        if(StringUtils.isEmpty(monitor.getName())){
            result.setCode(100);
            result.setMsg("params error");
            return result;
        }
        result.setData(monitor.getName());
        if(monitorDao.findCount(monitor.getName())>0){
            result.setCode(500);
            result.setMsg("already exist");
            return result;
        }

        try {
            monitorDao.insert(monitor);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(201);
            result.setMsg(e.getMessage());
            return  result;
        }
        result.setCode(0);
        result.setMsg("ok");
        return result;
    }
    public Result<String> update(Monitor monitor){
        Result<String> result = new Result<>();
        if(StringUtils.isEmpty(monitor.getName())){
            result.setCode(100);
            result.setMsg("params error");
            return result;
        }
        result.setData(monitor.getName());

        monitorDao.update(monitor);
        result.setCode(0);
        result.setMsg("ok");
        return result;
    }

    public Result<String> delete(String name){
        Result<String> result = new Result<>();
        if(StringUtils.isEmpty(name)){
            result.setCode(100);
            result.setMsg("params error");
            return result;
        }
        result.setData(name);
        monitorDao.delete(name);
        result.setCode(0);
        result.setMsg("ok");
        return result;
    }


    public Result<List<MonitorMetric>> findMetric(){
        List<MonitorMetric> metrics = metricDao.find();
        Result<List<MonitorMetric>> result = new Result<>();
        result.setCode(0);
        result.setMsg("ok");
        result.setData(metrics);
        return result;
    }

    public Result<List<MonitorMetric>> findMetric(MonitorTypeEnum typeEnum){
        List<MonitorMetric> monitors = metricDao.findByType(typeEnum);
        Result<List<MonitorMetric>> result = new Result<>();
        result.setCode(0);
        result.setMsg("ok");
        result.setData(monitors);
        return result;
    }



    public Result<MonitorMetric> getMetric(String name){
        Result<MonitorMetric> result = new Result<>();
        if(StringUtils.isBlank(name)){
            result.setCode(100);
            result.setMsg("params error");
            return result;
        }
        MonitorMetric metric = metricDao.get(name);
        result.setCode(0);
        result.setMsg("ok");
        result.setData(metric);
        return result;
    }

    public Result<String> saveMetric(MonitorMetric metric){
        Result<String> result = new Result<>();
        if(StringUtils.isEmpty(metric.getName())){
            result.setCode(100);
            result.setMsg("params error");
            return result;
        }
        result.setData(metric.getName());
        if(metricDao.findCount(metric.getName())>0){
            result.setCode(500);
            result.setMsg("already exist");
            return result;
        }

        try {
            metricDao.insert(metric);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(201);
            result.setMsg(e.getMessage());
            return  result;
        }
        result.setCode(0);
        result.setMsg("ok");
        return result;
    }
    public Result<String> updateMetric(MonitorMetric metric){
        Result<String> result = new Result<>();
        if(StringUtils.isEmpty(metric.getName())){
            result.setCode(100);
            result.setMsg("params error");
            return result;
        }
        result.setData(metric.getName());

        metricDao.update(metric);
        result.setCode(0);
        result.setMsg("ok");
        return result;
    }

    public Result<String> deleteMetric(String name){
        Result<String> result = new Result<>();
        if(StringUtils.isEmpty(name)){
            result.setCode(100);
            result.setMsg("params error");
            return result;
        }
        result.setData(name);
        metricDao.delete(name);
        result.setCode(0);
        result.setMsg("ok");
        return result;
    }

}
