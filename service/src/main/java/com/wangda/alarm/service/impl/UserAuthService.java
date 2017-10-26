package com.wangda.alarm.service.impl;

import com.wangda.alarm.service.common.util.WebUtil;
import com.wangda.alarm.service.dao.UserInfoDao;
import com.wangda.alarm.service.dao.po.UserInfoPo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-26
 */
@Service
public class UserAuthService {

    @Resource
    UserInfoDao userInfoDao;

    public UserInfoPo auth(String userName, String password) {
        UserInfoPo userInfoPo = userInfoDao.authUser(userName);
        if (userInfoPo == null) {
            return null;
        }

        String comparePass = WebUtil.md5(password, userInfoPo.getSalt());
        if (comparePass.equals(password)) {
            return userInfoPo;
        }
        return null;
    }
}
