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
    private final static int ERROR_COUNT = 3;
    private final static String LOGIN_ERR_KEY_PRE = "login_error_count_key_";

    /**
     * 登录session10天过期
     */
    private final static long SESSION_EXPIRATION = 10 * 24 *60 * 60;

    @Resource
    SimpleRedisClient simpleRedisClient;

    public void saveUserSession(UserSession session) {
        simpleRedisClient.setex(session.getToken(), JsonUtil.of(session), SESSION_EXPIRATION);
    }

    public void remoteUserSession(String token) {
        simpleRedisClient.del(token);
    }

    public void recordLogError(String userName) {
        simpleRedisClient.incr(userName);
    }

    public void delLogError(String userName) {
        simpleRedisClient.del(userName);
    }

    public boolean isForbiddenLogin(String userName) {
        String s = simpleRedisClient.get(userName);
        if (Integer.valueOf(s) > ERROR_COUNT) {
            return true;
        }
        return false;
    }

    public UserSession queryUserSession(String token) {
        Object object = simpleRedisClient.get(token, UserSession.class);
        if (object != null) return (UserSession) object;
        return null;
    }

    private String recoreErrorLoginKey(String userName) {
        return LOGIN_ERR_KEY_PRE + userName;
    }
}
