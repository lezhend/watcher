package com.fortinet.fcasb.watcher.alert.init;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by zliu on 2018/3/28.
 */
@Service
@Scope("singleton")
@DisconfFile(filename="alert.properties")
public class AlertProperties {
//    @Value("${config.log.path}")
//    @Value("${mail.smtp.host}")
//    @Value("${mail.smtp.port}")
//    @Value("${mail.smtp.username}")
//    @Value("${mail.smtp.password}")
//    @Value("${mail.from.address}")
//    @Value("${mail.smtp.auth}")
//    @Value("${mail.smtp.timeout}")

    private String logbackPath;
    private String dataPath;
    private String smtpHost;
    private Integer smtpPort;
    private String username;
    private String password;
    private String from;
    private String auth;
    private  int timeout;

    private String sendmails;

    private int alertPeriod;

    private int monitorPeriod;

    private String esHosts;
    private String esPorts;
    @DisconfFileItem(name = "es.server.ports", associateField = "esPorts")
    public String getEsPorts() {
        return esPorts;
    }

    public void setEsPorts(String esPorts) {
        this.esPorts = esPorts;
    }

    @DisconfFileItem(name = "es.server.hosts", associateField = "esHosts")
    public String getEsHosts() {
        return esHosts;
    }

    public void setEsHosts(String esHosts) {
        this.esHosts = esHosts;
    }

    @DisconfFileItem(name = "send.mail.list", associateField = "sendmails")
    public String getSendmails() {
        return sendmails;
    }

    public void setSendmails(String sendmails) {
        this.sendmails = sendmails;
    }

    @DisconfFileItem(name = "alert.period", associateField = "alertPeriod")
    public int getAlertPeriod() {
        return alertPeriod;
    }

    public void setAlertPeriod(int alertPeriod) {
        this.alertPeriod = alertPeriod;
    }
    @DisconfFileItem(name = "monitor.period", associateField = "monitorPeriod")
    public int getMonitorPeriod() {
        return monitorPeriod;
    }

    public void setMonitorPeriod(int monitorPeriod) {
        this.monitorPeriod = monitorPeriod;
    }

    @DisconfFileItem(name = "config.log.path", associateField = "logbackPath")
    public String getLogbackPath() {
        return logbackPath;
    }

    public void setLogbackPath(String logbackPath) {
        this.logbackPath = logbackPath;
    }
    @DisconfFileItem(name = "config.data.path", associateField = "dataPath")
    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    @DisconfFileItem(name = "mail.smtp.host", associateField = "smtpHost")
    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }
    @DisconfFileItem(name = "mail.smtp.port", associateField = "smtpPort")
    public Integer getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(Integer smtpPort) {
        this.smtpPort = smtpPort;
    }
    @DisconfFileItem(name = "mail.smtp.username", associateField = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @DisconfFileItem(name = "mail.smtp.password", associateField = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @DisconfFileItem(name = "mail.from.address", associateField = "from")
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
    @DisconfFileItem(name = "mail.smtp.auth", associateField = "auth")
    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
    @DisconfFileItem(name = "mail.smtp.timeout", associateField = "timeout")
    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
