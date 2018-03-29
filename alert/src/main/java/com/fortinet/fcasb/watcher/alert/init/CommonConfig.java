package com.fortinet.fcasb.watcher.alert.init;


import ch.qos.logback.ext.spring.LogbackConfigurer;
import com.baidu.disconf.client.DisconfMgrBean;
import com.baidu.disconf.client.DisconfMgrBeanSecond;
import com.fortinet.fcasb.watcher.alert.domain.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
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
@Import({DBConfig.class})
@Configuration
public class CommonConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonConfig.class);


    /***temporarily comment out this disconf bean ******
     * @param ctx
     * @return
     */
    @Bean(destroyMethod="destroy")
    public DisconfMgrBean disconfMgrBean(ApplicationContext ctx){
        DisconfMgrBean ret = new DisconfMgrBean();
        ret.setScanPackage("com.fortinet.fcasb.watcher.alert");
        return ret;
    }


    @Bean(initMethod="init",destroyMethod="destroy")
    public DisconfMgrBeanSecond DisconfMgrBean2(ApplicationContext ctx){
        return new DisconfMgrBeanSecond();

    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        taskScheduler.initialize();
        return taskScheduler;
    }

    @Bean
    public Logger initLogback(@Autowired AlertProperties alertProperties){
        try {
            LogbackConfigurer.initLogging(alertProperties.getLogbackPath());
        } catch (Exception e) {
            LOGGER.error("Loading logback config file failed , please config logback file path：{}","classpath:/conf/logback.xml");
        }
        return LOGGER;
    }

    @Bean
    public JavaMailSender javaMailSender(@Autowired AlertProperties alertProperties) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(alertProperties.getSmtpHost());
        mailSender.setPort(alertProperties.getSmtpPort());
        mailSender.setUsername(alertProperties.getUsername());
        mailSender.setPassword(alertProperties.getPassword());
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", alertProperties.getAuth());
        prop.put("mail.smtp.timeout", alertProperties.getTimeout());
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.socketFactory.fallback", "false");
        prop.put("mail.smtp.socketFactory.port", alertProperties.getSmtpPort());
        mailSender.setJavaMailProperties(prop);
        return mailSender;
    }

    @Bean
    public SimpleMailMessage simpleMailMessage(@Autowired AlertProperties alertProperties) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(alertProperties.getFrom());
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

