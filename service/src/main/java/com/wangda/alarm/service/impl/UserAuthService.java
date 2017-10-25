package com.wangda.alarm.service.impl;

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

        if (userInfoPo.getPassword().equals(password)) {
            return userInfoPo;
        }
        return null;
    }
}
