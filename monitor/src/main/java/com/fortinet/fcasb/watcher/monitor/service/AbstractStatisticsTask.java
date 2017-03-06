package com.fortinet.fcasb.watcher.monitor.service;

import com.alibaba.fastjson.JSON;
import com.fortinet.fcasb.watcher.monitor.dao.StatisticsDao;
import com.fortinet.fcasb.watcher.monitor.domain.Statistics;
import com.fortinet.fcasb.watcher.monitor.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zliu on 17/3/6.
 */
public abstract class AbstractStatisticsTask implements ITask{
    public static final Logger LOGGER = LoggerFactory.getLogger(AbstractStatisticsTask.class);

    public abstract StatisticsDao getStatisticsDao();

    @Override
    public void execute(){
        Statistics statistics = this.getInfo();
        if(statistics!=null) {
            LogUtil.LOGGER_MONITOR_STATISTIC.info(JSON.toJSONString(statistics));
            this.getStatisticsDao().insert(statistics);
        }else{
            LOGGER.error("statistics get error");
        }
    }

}
