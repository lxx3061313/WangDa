package com.wangda.alarm.service.impl;

import com.wangda.alarm.service.bean.biz.UserSession;
import com.wangda.alarm.service.common.util.cache.redisclient.SimpleRedisClient;
import com.wangda.alarm.service.common.util.json.JsonUtil;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-25
 */
@Service
public class LoginSessionService {

    @Resource
    SimpleRedisClient simpleRedisClient;

    public void saveUserSession(UserSession session) {
        simpleRedisClient.set(session.getToken(), JsonUtil.of(session));
    }

    public UserSession queryUserSession(String token) {
        Object object = simpleRedisClient.get(token, UserSession.class);
        if (object != null) return (UserSession) object;
        return null;
    }
}
