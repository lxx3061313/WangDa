package com.wangda.alarm.service.common.util.mail;

import com.wangda.alarm.service.bean.MailSendInfo;
import java.util.Date;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-11-04
 */
@Service
public class MailSender {

    @Value("${mail.username}")
    private String mailFrom;

    @Value("${mail.smtp.server}")
    private String smtpServer;

    @Value("${mail.auth.password}")
    private String authPassword;

    private Properties mailProperties = new Properties();

    @PostConstruct
    private void initMail() {
        mailProperties.setProperty("mail.transport.protocol", "smtp");
        mailProperties.setProperty("mail.smtp.host", smtpServer);
        mailProperties.setProperty("mail.smtp.auth", "true");
    }

    public void sendMail(String fromMail,String toMail, String subject, String context)
            throws MessagingException {
        Session session = Session.getInstance(mailProperties);
        session.setDebug(true);
        MimeMessage message = createMimeMessage(session, fromMail, toMail, subject, context);
        Transport transport = session.getTransport();
        transport.connect(mailFrom, authPassword);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    public void sendMail(MailSendInfo sendInfo) throws MessagingException {
        sendMail(sendInfo.getMailFrom(), sendInfo.getMailTo(), sendInfo.getSubject(), sendInfo.getContext());
    }

    public void sendMail(String toMail, String subject, String context)
            throws MessagingException {
        sendMail(mailFrom, toMail, subject, context);
    }

    private MimeMessage createMimeMessage(Session session, String fromMail,  String toMail, String subject,
            String context)
            throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromMail));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(toMail));
        message.setSubject(subject);
        message.setText(context);
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }
}
