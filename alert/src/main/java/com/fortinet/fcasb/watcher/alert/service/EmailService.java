package com.fortinet.fcasb.watcher.alert.service;

import com.fortinet.fcasb.watcher.alert.domain.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.text.MessageFormat;

/**
 * Created by zliu on 17/3/15.
 */
@Service
public class EmailService {
    public static final Logger LOGGER = LoggerFactory.getLogger(AlertService.class);


    @Value("${send.mail.list}")
    private String notemmail;


    public void sendAlert(Alert alert,String value,long count){
        String title = alert.getName()+" "+alert.getIndex()+" Alert!!!";
        String content = MessageFormat.format("Value {1} > {2}, Count {3} > {4}",value,alert.getConditionvalue(),count,
                alert.getConditioncount());
        String to = alert.getNotifications()+","+notemmail;
        LOGGER.info("title= {}",title);
        LOGGER.info("content= {}",content);
        LOGGER.info("to= {}",to);
        sendMail(title, content, to);
    }

    private @Autowired
    JavaMailSender javaMailSender;
    private @Autowired
    SimpleMailMessage simpleMailMessage;
    private @Autowired
    TaskExecutor taskExecutor;

    public void sendMail(String title, String content, String to) {
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setFrom(simpleMailMessage.getFrom());
            messageHelper.setSubject(title);
            messageHelper.setText(content,true);
            String[] mails = to.split(",");
            messageHelper.setTo(mails);

            LOGGER.debug("ready to send a mail to [{}]", to);
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    javaMailSender.send(mimeMessage);
                }
            });
        } catch (Exception e) {
            LOGGER.error("send email error", e);

        }
    }
}
