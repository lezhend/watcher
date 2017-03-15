package com.fortinet.fcasb.watcher.alert.service;

import com.fortinet.fcasb.watcher.alert.dao.AlertDao;
import com.fortinet.fcasb.watcher.alert.dao.AlertLogDao;
import com.fortinet.fcasb.watcher.alert.domain.Alert;
import com.fortinet.fcasb.watcher.alert.domain.AlertLog;
import com.fortinet.fcasb.watcher.alert.domain.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zliu on 17/3/3.
 */
@Service
public class AlertService {
    public static final Logger LOGGER = LoggerFactory.getLogger(AlertService.class);

    private List<Alert> ALERT_LIST = new ArrayList<>();


    @Autowired
    private AlertDao alertDao;
    @Autowired
    private AlertLogDao alertLogDao;

    @Autowired
    private ESService esService;

    @PostConstruct
    public void init(){
        refreshAlertList();
    }

    public boolean isTarget(Alert alert){
        //todo
        Object response =  esService.search(alert);
        //判断数量是否匹配，如果匹配进行value比对

        //value比对成功或者value为空表示命中否则返回false

        //命中后alertLog 写入
        if(false) {
            AlertLog alertLog = new AlertLog();
            alertLog.setName(alert.getName());
            alertLog.setContent(response.toString());
            alertLog.setNotifications(alert.getNotifications());
            alertLogDao.insert(alertLog);
        }
        return false;
    }

    public List<Alert> getAlertList(){
        return ALERT_LIST;
    }

    public void refreshAlertList(){
        Result<List<Alert>> result = find();
        if(result.getCode()==0){
            ALERT_LIST = result.getData();
        }
    }

    public Result<List<Alert>> find(){
        List<Alert> alertList = alertDao.find();
        Result<List<Alert>> result = new Result<>();
        result.setCode(0);
        result.setMsg("ok");
        result.setData(alertList);
        return result;
    }

    public Result<List<AlertLog>> findLogs(){
        List<AlertLog> alertList = alertLogDao.find();
        Result<List<AlertLog>> result = new Result<>();
        result.setCode(0);
        result.setMsg("ok");
        result.setData(alertList);
        return result;
    }
    public Result<List<AlertLog>> findLogsByName(String name){
        List<AlertLog> alertList = alertLogDao.get(name);
        Result<List<AlertLog>> result = new Result<>();
        result.setCode(0);
        result.setMsg("ok");
        result.setData(alertList);
        return result;
    }

    public Result<Alert> get(String name){
        Result<Alert> result = new Result<>();
        if(StringUtils.isEmpty(name)){
            result.setCode(100);
            result.setMsg("params error");
            return result;
        }
        Alert alert = alertDao.get(name);
        result.setCode(0);
        result.setMsg("ok");
        result.setData(alert);
        return result;
    }

    public Result<String> save(Alert alert){
        Result<String> result = new Result<>();
        if(StringUtils.isEmpty(alert.getName()) || StringUtils.isEmpty(alert.getIndex())){
            result.setCode(100);
            result.setMsg("params error");
            return result;
        }
        result.setData(alert.getName());
        if(alertDao.findCount(alert.getName())>0){
            result.setCode(500);
            result.setMsg("already exist");
            return result;
        }
        alertDao.insert(alert);
        result.setCode(0);
        result.setMsg("ok");
        return result;
    }
    public Result<String> update(Alert alert){
        Result<String> result = new Result<>();
        if(StringUtils.isEmpty(alert.getName()) || StringUtils.isEmpty(alert.getIndex())){
            result.setCode(100);
            result.setMsg("params error");
            return result;
        }
        result.setData(alert.getName());
        alertDao.update(alert);
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
        alertDao.delete(name);
        result.setCode(0);
        result.setMsg("ok");
        return result;
    }

}
