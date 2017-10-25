package com.wangda.alarm.service.bean.biz;

/**
 * @author chao.zhu
 * @version 2016-07-01
 */
public class UserLoginContext {
    private static ThreadLocal<UserSession> userThreadLocal = new ThreadLocal<UserSession>();

    public static UserSession getUser() {
        return userThreadLocal.get();
    }

    public static void setUser(UserSession user) {
        userThreadLocal.set(user);
    }

    public static void release() {
        userThreadLocal.remove();
    }
}
