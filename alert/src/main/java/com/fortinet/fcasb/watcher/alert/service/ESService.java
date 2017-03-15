package com.fortinet.fcasb.watcher.alert.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by zliu on 17/3/3.
 */
@Service
public class ESService {
    public static final Logger LOGGER = LoggerFactory.getLogger(ESService.class);

    @Value("${es.host}")
    private String esHost;


    @PostConstruct
    public void init(){

    }



}
