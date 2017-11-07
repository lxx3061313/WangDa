package com.wangda.alarm.service.common.tcplayer;

import com.wangda.alarm.service.common.message.MessageDispatcher;
import com.wangda.alarm.service.common.tcplayer.common.SessionRegCenter;
import com.wangda.alarm.service.common.util.json.JsonUtil;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Resource;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
@Service("serverHandler")
public class ServerHandler extends IoHandlerAdapter {
    private final static Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    public static ConcurrentHashMap<String, IoSession> sessionsConcurrentHashMap = new ConcurrentHashMap<>();
    public static String sessionKey = "session_key";

    @Resource
    MessageDispatcher messageDispatcher;

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        logger.warn("服务器收到的消息是：" + JsonUtil.of(message));
        messageDispatcher.dispatch(session, message);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        logger.warn("messageSent:" + message);
    }
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        logger.warn("remote client [" + session.getRemoteAddress().toString() + "] connected.");
        Long time = System.currentTimeMillis();
        session.setAttribute("id", time);
        SessionRegCenter.reg(session);
    }
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        logger.warn("sessionClosed.");
        session.closeOnFlush();
        SessionRegCenter.remove();
    }
    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        logger.warn("session idle, so disconnecting......");
        session.closeOnFlush();
        logger.warn("disconnected.");
    }
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        logger.warn("sessionOpened.");
        //session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, IDLE);
    }
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        session.closeOnFlush();
        logger.warn("session occured exception, so close it." + cause.getMessage());
    }

    public IoSession getSession() {
        return sessionsConcurrentHashMap.get(sessionKey);
    }
}
