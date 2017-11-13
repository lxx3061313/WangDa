package com.wangda.alarm.service.common.util.mail;

import com.sun.mail.util.MailSSLSocketFactory;
import com.wangda.alarm.service.bean.MailSendInfo;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-11-04
 */
@Service
public class MailSender {
    private final static Logger logger = LoggerFactory.getLogger(MailSender.class);

    @Value("${mail.smtp.server}")
    private String smtpServer;

    @Value("${mail.auth.password}")
    private String authPassword;

    @Value("${mail.auth.username}")
    private String authUserName;

    private Properties mailProperties = new Properties();

    @PostConstruct
    private void initMail() {
        mailProperties.setProperty("mail.transport.protocol", "smtp");
        mailProperties.setProperty("mail.smtp.host", smtpServer);
        mailProperties.setProperty("mail.smtp.auth", "true");
        mailProperties.setProperty("mail.smtp.port", "465");
        mailProperties.put("mail.smtp.ssl.enable", "true");
    }

    public void sendMail(String fromMail,String toMail, String subject, String context)
            throws MessagingException {
        Session session;
        try {
            session = getSession();
            session.setDebug(true);
            MimeMessage message = createMimeMessage(session, fromMail, toMail, subject, context);
            Transport transport = session.getTransport();
            transport.connect(authUserName, authPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            logger.error("邮件发送失败", e);
            throw new RuntimeException(e);
        }
    }

    public Session getSession() throws GeneralSecurityException {
        MailSSLSocketFactory sf  = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        mailProperties.put("mail.smtp.ssl.socketFactory", sf);
        return Session.getInstance(mailProperties, new SMTPAuthenticator(authUserName, authPassword));
    }

    public void sendMail(MailSendInfo sendInfo) throws MessagingException {
        sendMail(sendInfo.getMailFrom(), sendInfo.getMailTo(), sendInfo.getSubject(), sendInfo.getContext());
    }

    public void sendMail(String toMail, String subject, String context)
            throws MessagingException {
        sendMail(authUserName, toMail, subject, context);
    }

    private MimeMessage createMimeMessage(Session session, String fromMail,  String toMail, String subject,
            String context)
            throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromMail));
        message.setRecipient(RecipientType.CC, new InternetAddress(authUserName));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(toMail));
        message.setSubject(subject);
        message.setText(context);
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

    class SMTPAuthenticator extends Authenticator {
        private String username;
        private String password;

        public SMTPAuthenticator(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    }
}
