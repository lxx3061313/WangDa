package com.wangda.alarm.service.common.tcplayer.common;

import java.util.concurrent.ConcurrentHashMap;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Service;

/**
 * @author wenlai
 * @version 2017-11-07
 */
@Service
public class SessionRegCenter {
    public static ConcurrentHashMap<String, IoSession> sessionsConcurrentHashMap = new ConcurrentHashMap<>();
    public static String sessionKey = "session_key";

    public static void reg(IoSession session) {
        sessionsConcurrentHashMap.put(sessionKey, session);
    }

    public static void remove() {
        sessionsConcurrentHashMap.remove(sessionKey);
    }

    public static IoSession getSession() {
        return sessionsConcurrentHashMap.get(sessionKey);
    }
}
