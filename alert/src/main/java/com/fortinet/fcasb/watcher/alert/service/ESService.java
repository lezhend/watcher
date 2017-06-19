package com.fortinet.fcasb.watcher.alert.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fortinet.fcasb.watcher.alert.domain.Alert;
import com.fortinet.fcasb.watcher.alert.init.RestWrapper;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zliu on 17/3/3.
 */
@Service
public class ESService {
    public static final Logger LOGGER = LoggerFactory.getLogger(ESService.class);

    private static final String SEARCH_DEFAULT_TYPE="log";
    private static final int SEARCH_SIZE=1000;
    private static final int SEARCH_FROM=0;

    @Value("${alert.period}")
    private Integer period;

    @Value("${es.server.hosts}")
    private String[] hosts;

    @Value("${es.server.ports}")
    private String[] ports;

    @Autowired
    private RestWrapper restWrapper;


    private Map<String,Object> restSearch(Alert alert,Map<String,Object> searchFilter){
        String host = hosts[0];
        String port = ports[0];
        if(StringUtils.isNotBlank(alert.getHost())&& StringUtils.isNumeric(alert.getPort())){
             host = alert.getHost();
             port = alert.getPort();
        }
        String url = "http://"+host+":"+port+"/"+alert.getIndex()+"/_search";
        ResponseEntity<Map<String,Object>> re = restWrapper.post(url,searchFilter,new TypeReference<Map<String,Object>>(){});
        return re.getBody();
    }


    public Map<String,Object> search(Alert alert) throws Exception {
        //根据alert查询条件
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        if(StringUtils.isNotBlank(alert.getSearchkey())) {
            boolQueryBuilder.must(QueryBuilders.queryStringQuery(alert.getSearchkey()));
        }

        if(alert.getFilter()!=null){
            for(Map.Entry<String,String> value:alert.getFilter().entrySet()){
                boolQueryBuilder.must(QueryBuilders.termQuery(value.getKey(), value.getValue()));
            }
        }

        boolQueryBuilder.must(QueryBuilders.rangeQuery("@timestamp").format("epoch_millis").gte(System.currentTimeMillis() - period * 1000).lte(System.currentTimeMillis()));

        Map<String,Object> params = new HashMap<>();
        params.put("query", JSON.parse(boolQueryBuilder.toString()));
        params.put("size",SEARCH_SIZE);
        return restSearch(alert,params);


    }



}
