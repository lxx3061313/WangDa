package com.wangda.alarm.service.impl;

import com.wangda.alarm.service.bean.MailSendInfo;
import java.text.MessageFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-11-04
 */
@Service
public class MailContextBuilder {

    @Value("${mail.username}")
    private String mailFrom;

    private final static String FORGET_PASS_MAILSUB = "作业追踪系统用户密码重置";

    public MailSendInfo buildForgetPssMail (String account, String mailTo, String newPss) {
        MailSendInfo sendInfo = new MailSendInfo();
        sendInfo.setMailFrom(mailFrom);
        sendInfo.setMailTo(mailTo);
        sendInfo.setSubject(FORGET_PASS_MAILSUB);
        sendInfo.setContext(MessageFormat.format("尊敬的 {0} 您好, 您账户重置后为:{1}", account, newPss));
        return sendInfo;
    }
}
