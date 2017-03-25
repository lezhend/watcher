package com.fortinet.fcasb.watcher.alert.service;

import com.fortinet.fcasb.watcher.alert.dao.AlertDao;
import com.fortinet.fcasb.watcher.alert.dao.AlertLogDao;
import com.fortinet.fcasb.watcher.alert.domain.Alert;
import com.fortinet.fcasb.watcher.alert.domain.AlertLog;
import com.fortinet.fcasb.watcher.alert.domain.Result;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    public ResultTarget isTarget(Alert alert){
        boolean isTarg = false;
        SearchHits response = null;

        ResultTarget resultTarget = new ResultTarget();
        try {
            response = esService.search(alert);
            resultTarget.setCount(response.getTotalHits());
            resultTarget.setAlert(alert);
            if(StringUtils.isNumeric(alert.getConditioncount())){
                 if(response.getTotalHits()>Integer.valueOf(alert.getConditioncount())){
                     isTarg = true;
                 }
            } else {
                if (StringUtils.isNotBlank(alert.getField()) && StringUtils.isNotBlank(alert.getConditionvalue())) {
                    String value = "";
                    for (SearchHit searchHitFields : response.getHits()) {
                        Map<String, Object> values = searchHitFields.getSource();
                        for (String key : values.keySet()) {
                            if (StringUtils.isNotBlank(alert.getField())) {
                                if (key.equalsIgnoreCase(alert.getField())) {
                                    if (values.get(key) != null && StringUtils.isNumeric(values.get(key).toString()) && StringUtils.isNumeric(alert.getConditionvalue())) {
                                        if (Long.valueOf(values.get(key).toString()) > Long.valueOf(alert.getConditionvalue())) {
                                            isTarg = true;
                                            value = values.get(key).toString();
                                            break;
                                        }
                                    } else {
                                        if (values.get(key) != null) {
                                            if (alert.getConditionvalue().compareToIgnoreCase(values.get(key).toString()) < 0) {
                                                isTarg = true;
                                                value = values.get(key).toString();
                                                break;
                                            }
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                        if(isTarg){
                            resultTarget.setValue(value);
                            break;
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //命中后alertLog 写入
        if(isTarg) {
            AlertLog alertLog = new AlertLog();
            alertLog.setName(alert.getName());
            alertLog.setContent("");
            alertLog.setNotifications(alert.getNotifications());
            alertLogDao.insert(alertLog);
        }
        resultTarget.setTarget(isTarg);
        return resultTarget;
    }

    public List<Alert> getAlertList(){
        return ALERT_LIST;
    }

    public Result refreshAlertList(){
        Result<List<Alert>> result = find();
        if(result.getCode()==0){
            ALERT_LIST.clear();
            ALERT_LIST = result.getData();
        }
        return result;
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
        if(StringUtils.isBlank(name)){
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

    public class ResultTarget{
        private boolean isTarget;
        private String value;
        private long count;
        private Alert alert;

        public boolean isTarget() {
            return isTarget;
        }

        public void setTarget(boolean isTarget) {
            this.isTarget = isTarget;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }

        public Alert getAlert() {
            return alert;
        }

        public void setAlert(Alert alert) {
            this.alert = alert;
        }
    }

}
