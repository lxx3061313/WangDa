package com.wangda.alarm.service.impl;

import com.google.common.base.Strings;
import com.wangda.alarm.service.bean.biz.UserInfo;
import com.wangda.alarm.service.common.util.TokenGenerator;
import com.wangda.alarm.service.common.util.mail.MailSender;
import com.wangda.alarm.service.common.util.pojo.BizException;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lixiaoxiong
 * @version 2017-11-04
 */
@Service
public class ResetPassService {
    private final static Logger logger = LoggerFactory.getLogger(ResetPassService.class);

    @Resource
    UserInfoService userInfoService;

    @Resource
    MailSender mailSender;

    @Resource
    MailContextBuilder mailContextBuilder;

    @Transactional(rollbackFor = Exception.class)
    public void resetPass(String account) {
        UserInfo userInfo = userInfoService.queryUserInfo(account);
        if (userInfo == null) {
            throw new BizException("账户不存在");
        }

        if (Strings.isNullOrEmpty(userInfo.getEmail())) {
            throw new BizException("邮箱信息不存在,无法重置密码");
        }

        String newPass = TokenGenerator.generateRandomPass(account);
        try {
            mailSender.sendMail(mailContextBuilder.buildForgetPssMail(
                    userInfo.getAccount(), userInfo.getEmail(), newPass));
            userInfoService.updatePassword(account, newPass, userInfo.getSalt());
        } catch (MessagingException e) {
            logger.error("发送重置邮件错误", e);
            throw new BizException("发送重置邮件错误");
        }
    }

    public void resetPass(String account, String newPass, String salt) {
        userInfoService.updatePassword(account, newPass, salt);
    }
}
