package com.fortinet.fcasb.watcher.alert.init;


import ch.qos.logback.ext.spring.LogbackConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by zliu on 17/1/27.
 */
@ComponentScan(basePackages = {
        "com.fortinet.fcasb.watcher.alert"
})
@PropertySource(value = {
//        "classpath:/alert.properties",
        "file:/opt/alert/alert.properties"
}, ignoreResourceNotFound = true)
@Configuration
public class CommonConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonConfig.class);

    @Value("${config.log.path}")
    private String logbackPath;

    @Value("${mail.smtp.host}")
    private String smtpHost;
    @Value("${mail.smtp.port}")
    private Integer smtpPort;
    @Value("${mail.smtp.username}")
    private String username;
    @Value("${mail.smtp.password}")
    private String password;

    @Value("${mail.from.address}")
    private String from;
    private @Value("${mail.smtp.auth}") String auth;
    private @Value("${mail.smtp.timeout}") int timeout;


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

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpHost);
        mailSender.setPort(smtpPort);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", auth);
        prop.put("mail.smtp.timeout", timeout);
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.socketFactory.fallback", "false");
        prop.put("mail.smtp.socketFactory.port", smtpPort);
        mailSender.setJavaMailProperties(prop);
        return mailSender;
    }

    @Bean
    public SimpleMailMessage simpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        return simpleMailMessage;
    }

    @Bean
    public RestTemplate getRestTemplate() {
        LOGGER.info("init RestTemplate");
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> hmcList = new ArrayList<>();
        hmcList.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(hmcList);
        return restTemplate;
    }
}

