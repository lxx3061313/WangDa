package com.wangda.alarm.service.bean;

/**
 * @author lixiaoxiong
 * @version 2017-11-04
 */
public class MailSendInfo {
    private String mailFrom;
    private String mailTo;
    private String subject;
    private String context;

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
