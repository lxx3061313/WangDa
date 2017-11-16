package com.wangda.alarm.service.impl;

import com.google.common.base.Strings;
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
    private final static String USER_SESSION_TOKEN_KEY = "token_key_";
    private final static String LOGIN_ERROR_RECORD_PRE = "login_error_record_";

    private final static long FORBIDDEN_EXPIRATION = 6 * 60 * 60;

    @Resource
    SimpleRedisClient simpleRedisClient;

    public void saveUserSession(UserSession session, int secsOfExpire) {
        simpleRedisClient.setex(session.getToken(), session, secsOfExpire);
        simpleRedisClient.set(USER_SESSION_TOKEN_KEY+session.getUserName(), session.getToken());
    }

    public boolean isUserOnline(String userName) {
        String token = simpleRedisClient.get(USER_SESSION_TOKEN_KEY + userName);
        if (Strings.isNullOrEmpty(token)) {
            return false;
        }
        String session = simpleRedisClient.get(token);
        return session != null;
    }

    public void remoteUserSession(String token) {
        simpleRedisClient.del(token);
    }

    public void recordLogError(String userName) {
        simpleRedisClient.incr(loginErrorKey(userName));
        String in = simpleRedisClient.get(loginErrorKey(userName));
        simpleRedisClient.setex(loginErrorKey(userName), Integer.valueOf(in), FORBIDDEN_EXPIRATION);
    }

    public void delLogError(String userName) {
        simpleRedisClient.del(userName);
    }

    public boolean isForbiddenLogin(String userName) {
        String s = simpleRedisClient.get(loginErrorKey(userName));
        if (!Strings.isNullOrEmpty(s) && Integer.valueOf(s) > ERROR_COUNT) {
            return true;
        }
        return false;
    }

    public UserSession queryUserSession(String token) {
        Object object = simpleRedisClient.get(token, UserSession.class);
        if (object != null) return (UserSession) object;
        return null;
    }

    private String loginErrorKey(String userName) {
        return LOGIN_ERROR_RECORD_PRE + userName;
    }

    private String recoreErrorLoginKey(String userName) {
        return LOGIN_ERR_KEY_PRE + userName;
    }
}
