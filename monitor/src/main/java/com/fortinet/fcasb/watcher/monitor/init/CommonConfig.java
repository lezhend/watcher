package com.fortinet.fcasb.watcher.monitor.init;


import ch.qos.logback.ext.spring.LogbackConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Created by zliu on 17/1/27.
 */
@ComponentScan(basePackages = {
        "com.fortinet.fcasb.watcher.monitor"
})
@PropertySource(value = {
//        "classpath:./monitor.properties",
        "file:/opt/monitor/monitor.properties"
}, ignoreResourceNotFound = true)
@Configuration
public class CommonConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonConfig.class);

    @Value("${config.log.path}")
    private String logbackPath;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        return new ThreadPoolTaskScheduler();
    }

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

