package com.fortinet.fcasb.watcher.alert.service;

import com.fortinet.fcasb.watcher.alert.domain.Alert;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zliu on 17/3/3.
 */
@Service
public class ESService {
    public static final Logger LOGGER = LoggerFactory.getLogger(ESService.class);

    @Autowired
    public TransportClient client;


    public Object search(Alert alert){
        //根据alert查询条件
        QueryBuilder qb = QueryBuilders.queryStringQuery(alert.getFilter());
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch().setQuery(qb);
        MultiSearchResponse sr = client.prepareMultiSearch().get();
        for (MultiSearchResponse.Item item : sr.getResponses()) {
            SearchResponse response = item.getResponse();
        }
        return null;
    }



}
