package com.fortinet.fcasb.watcher.esmonitor.init;

import ch.qos.logback.ext.spring.LogbackConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by zliu on 17/1/27.
 */
@ComponentScan(basePackages = {
        "com.fortinet.fcasb.watcher.esmonitor"
})
@PropertySource(value = {
        "classpath:/esmonitor.properties",
        "file:/opt/esmonitor/esmonitor.properties"
}, ignoreResourceNotFound = true)
@Configuration
public class CommonConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonConfig.class);

    @Value("${config.log.path}")
    private String logbackPath;


    @Bean
    public Logger initLogback(){
        try {
            LogbackConfigurer.initLogging(logbackPath);
        } catch (Exception e) {
            LOGGER.error("Loading logback config file failed , please config logback file path：{}","classpath:/conf/logback.xml");
        }
        return LOGGER;
    }
}

