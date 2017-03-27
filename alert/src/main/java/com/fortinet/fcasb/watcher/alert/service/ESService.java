package com.fortinet.fcasb.watcher.alert.service;

import com.fortinet.fcasb.watcher.alert.domain.Alert;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    @Autowired
    private TransportClient client;


    public SearchHits search(Alert alert) throws Exception {
        //根据alert查询条件
        SearchRequestBuilder builder= client.prepareSearch(alert.getIndex())
//                .setTypes(SEARCH_DEFAULT_TYPE)
                .setSearchType(SearchType.DEFAULT)
                .setFrom(SEARCH_FROM)
                .setSize(SEARCH_SIZE);

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
        builder.setQuery(boolQueryBuilder);

        SearchResponse response =builder.execute().actionGet();
        SearchHits hits =  response.getHits();
        return hits;
    }



}
