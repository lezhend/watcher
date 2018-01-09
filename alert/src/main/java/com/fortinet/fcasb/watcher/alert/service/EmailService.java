package com.fortinet.fcasb.watcher.alert.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * Created by zliu on 17/3/15.
 */
@Service
public class EmailService {
    public static final Logger LOGGER = LoggerFactory.getLogger(AlertService.class);

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
            messageHelper.setSubject("[CMM-ALERT] "+title);
            messageHelper.setText(content,true);
            String[] mails = to.split(",");
            messageHelper.setTo(mails);

            LOGGER.debug("ready to send a mail to [{}]", to);
            taskExecutor.execute( () -> javaMailSender.send(mimeMessage));
        } catch (Exception e) {
            LOGGER.error("send email error", e);

        }
    }
}
