package com.fortinet.fcasb.watcher.alert.service;

import com.fortinet.fcasb.watcher.alert.dao.AlertDao;
import com.fortinet.fcasb.watcher.alert.dao.AlertLogDao;
import com.fortinet.fcasb.watcher.alert.domain.Alert;
import com.fortinet.fcasb.watcher.alert.domain.AlertLog;
import com.fortinet.fcasb.watcher.alert.domain.Result;
import com.fortinet.fcasb.watcher.alert.enums.AlertConditionEnum;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
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
        Map<String,Object> response = null;

        ResultTarget resultTarget = new ResultTarget();
        try {
            response = esService.search(alert);
            if(response.containsKey("hits")){
                Map<String,Object> hits = (Map<String, Object>) response.get("hits");
                List<Map<String,Object>> hitList = (List<Map<String, Object>>) hits.get("hits");
                resultTarget.setCount(Long.valueOf(hits.get("total").toString()));
                resultTarget.setAlert(alert);
                if(StringUtils.isNotBlank(alert.getConditioncount()) && StringUtils.isNumeric(alert.getConditioncount())){

                    if(alert.getCcount().equals(AlertConditionEnum.LE.getValue())){
                        if(resultTarget.getCount()<=Integer.valueOf(alert.getConditioncount())){
                            isTarg = true;
                        }
                    }else if(alert.getCcount().equals(AlertConditionEnum.GE.getValue())){
                        if(resultTarget.getCount()>=Integer.valueOf(alert.getConditioncount())){
                            isTarg = true;
                        }
                    }else if(alert.getCcount().equals(AlertConditionEnum.LT.getValue())){
                        if(resultTarget.getCount()<Integer.valueOf(alert.getConditioncount())){
                            isTarg = true;
                        }
                    }
                    else if(alert.getCcount().equals(AlertConditionEnum.GT.getValue())){
                        if(resultTarget.getCount()>Integer.valueOf(alert.getConditioncount())){
                            isTarg = true;
                        }
                    } else if(alert.getCcount().equals(AlertConditionEnum.EQ.getValue())){
                        if(resultTarget.getCount()==Integer.valueOf(alert.getConditioncount())){
                            isTarg = true;
                        }
                    }


                    if(isTarg){
                        boolean isGet = false;
                        if(StringUtils.isNotBlank(alert.getField())) {
                            for (Map<String,Object> searchHitFields : hitList) {
                                Map<String, Object> values = (Map<String, Object>) searchHitFields.get("_source");
                                for (String key : values.keySet()) {
                                    if (key.equalsIgnoreCase(alert.getField())) {
                                        resultTarget.setValue(values.get(key).toString());
                                        isGet = true;
                                        break;
                                    }
                                }
                                if(isGet){
                                    break;
                                }

                            }
                        }
                        if(!isGet){
                            resultTarget.setValue("No get value");
                        }
                    }
                 } else {
                    if (StringUtils.isNotBlank(alert.getField()) && StringUtils.isNotBlank(alert.getConditionvalue())) {
                        String value = "";
                        for (Map<String,Object> searchHitFields : hitList) {
                            Map<String, Object> values = (Map<String, Object>) searchHitFields.get("_source");
                            for (String key : values.keySet()) {
                                if (StringUtils.isNotBlank(alert.getField())) {
                                    if (key.equalsIgnoreCase(alert.getField())) {
                                        if (values.get(key) != null && StringUtils.isNumeric(values.get(key).toString()) && StringUtils.isNumeric(alert.getConditionvalue())) {

                                            if (alert.getCvalue().equals(AlertConditionEnum.LE.getValue())) {
                                                if (Long.valueOf(values.get(key).toString()) <= Long.valueOf(alert.getConditionvalue())) {
                                                    isTarg = true;
                                                    value = values.get(key).toString();
                                                    break;
                                                }
                                            } else if (alert.getCvalue().equals(AlertConditionEnum.GE.getValue())) {
                                                if (Long.valueOf(values.get(key).toString()) >= Long.valueOf(alert.getConditionvalue())) {
                                                    isTarg = true;
                                                    value = values.get(key).toString();
                                                    break;
                                                }
                                            } else if (alert.getCvalue().equals(AlertConditionEnum.LT.getValue())) {
                                                if (Long.valueOf(values.get(key).toString()) < Long.valueOf(alert.getConditionvalue())) {
                                                    isTarg = true;
                                                    value = values.get(key).toString();
                                                    break;
                                                }
                                            } else if (alert.getCvalue().equals(AlertConditionEnum.GT.getValue())) {
                                                if (Long.valueOf(values.get(key).toString()) > Long.valueOf(alert.getConditionvalue())) {
                                                    isTarg = true;
                                                    value = values.get(key).toString();
                                                    break;
                                                }
                                            } else if (alert.getCvalue().equals(AlertConditionEnum.EQ.getValue())) {
                                                if (Long.valueOf(values.get(key).toString()) == Long.valueOf(alert.getConditionvalue())) {
                                                    isTarg = true;
                                                    value = values.get(key).toString();
                                                    break;
                                                }
                                            }

                                        } else {
                                            if (values.get(key) != null) {
                                                if (alert.getCvalue().equals(AlertConditionEnum.LE.getValue())) {
                                                    if (values.get(key).toString().compareToIgnoreCase(alert.getConditionvalue()) <= 0) {
                                                        isTarg = true;
                                                        value = values.get(key).toString();
                                                        break;
                                                    }
                                                } else if (alert.getCvalue().equals(AlertConditionEnum.GE.getValue())) {
                                                    if (values.get(key).toString().compareToIgnoreCase(alert.getConditionvalue()) >= 0) {
                                                        isTarg = true;
                                                        value = values.get(key).toString();
                                                        break;
                                                    }
                                                } else if (alert.getCvalue().equals(AlertConditionEnum.LT.getValue())) {
                                                    if (values.get(key).toString().compareToIgnoreCase(alert.getConditionvalue()) < 0) {
                                                        isTarg = true;
                                                        value = values.get(key).toString();
                                                        break;
                                                    }
                                                } else if (alert.getCvalue().equals(AlertConditionEnum.GT.getValue())) {
                                                    if (values.get(key).toString().compareToIgnoreCase(alert.getConditionvalue()) > 0) {
                                                        isTarg = true;
                                                        value = values.get(key).toString();
                                                        break;
                                                    }
                                                } else if (alert.getCvalue().equals(AlertConditionEnum.EQ.getValue())) {
                                                    if (values.get(key).toString().compareToIgnoreCase(alert.getConditionvalue()) == 0) {
                                                        isTarg = true;
                                                        value = values.get(key).toString();
                                                        break;
                                                    }
                                                }


                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                            if (isTarg) {
                                resultTarget.setValue(value);
                                break;
                            }
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
            String content = MessageFormat.format("Value {0} > {1}, Count {2} > {3}", resultTarget.getValue(), alert.getConditionvalue(), resultTarget.getCount(),
                    alert.getConditioncount());

            if(StringUtils.isNotBlank(alert.getEmailtemplate()) ){
                content = alert.getEmailtemplate().replace("{count}", String.valueOf(resultTarget.getCount()));
                if(StringUtils.isNotBlank(resultTarget.getValue())) {
                    content = content.replace("{value}", resultTarget.getValue());
                }
            }
            alertLog.setContent(content);
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
        if(StringUtils.isBlank(alert.getCcount())){
            alert.setCcount(AlertConditionEnum.GE.getValue());
        }
        if(StringUtils.isBlank(alert.getCvalue())){
            alert.setCvalue(AlertConditionEnum.GE.getValue());
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
        if(StringUtils.isBlank(alert.getCcount())){
            alert.setCcount(AlertConditionEnum.GE.getValue());
        }
        if(StringUtils.isBlank(alert.getCvalue())){
            alert.setCvalue(AlertConditionEnum.GE.getValue());
        }
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
