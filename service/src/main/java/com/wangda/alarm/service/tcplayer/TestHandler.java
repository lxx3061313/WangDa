package com.wangda.alarm.service.tcplayer;

import com.wangda.alarm.service.util.json.JsonUtil;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
public class TestHandler extends IoHandlerAdapter {
    private final static Logger logger = LoggerFactory.getLogger(TestHandler.class);

    public static ConcurrentHashMap<Long, IoSession> sessionsConcurrentHashMap = new ConcurrentHashMap<Long, IoSession>();

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        logger.warn("客户端" + ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress() + "连接成功！");
        session.setAttribute("type", message);
        String remoteAddress = ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress();
        session.setAttribute("ip", remoteAddress);
        logger.warn("服务器收到的消息是：" + JsonUtil.of(message));
        //session.write("welcome by he");
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        logger.warn("messageSent:" + message);
    }
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        logger.warn("remote client [" + session.getRemoteAddress().toString() + "] connected.");
        // my
        Long time = System.currentTimeMillis();
        session.setAttribute("id", time);
        sessionsConcurrentHashMap.put(time, session);
    }
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        logger.warn("sessionClosed.");
        session.closeOnFlush();
        // my
        sessionsConcurrentHashMap.remove(session.getAttribute("id"));
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
        //  
        //session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, IDLE);
    }
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        session.closeOnFlush();
        logger.warn("session occured exception, so close it." + cause.getMessage());
    }
}
