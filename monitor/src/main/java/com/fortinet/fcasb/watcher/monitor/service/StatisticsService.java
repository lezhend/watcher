package com.fortinet.fcasb.watcher.monitor.service;

import com.fortinet.fcasb.watcher.monitor.dao.StatisticsDao;
import com.fortinet.fcasb.watcher.monitor.domain.Result;
import com.fortinet.fcasb.watcher.monitor.domain.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zliu on 17/3/3.
 */
@Service
public class StatisticsService {

    @Autowired
    private StatisticsDao statisticsDao;

    public Result<List<Statistics>> find(String startTime,String endTime){
        List<Statistics> statisticses = statisticsDao.find(new Date(),startTime,endTime);
        Result<List<Statistics>> result = new Result<>();
        result.setCode(0);
        result.setMsg("ok");
        result.setData(statisticses);
        return result;
    }

}
