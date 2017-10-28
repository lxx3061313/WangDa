package com.wangda.alarm.service.common.tcplayer;

import javax.annotation.PostConstruct;
import org.apache.mina.core.session.IoSession;

/**
 * @author lixiaoxiong
 * @version 2017-10-28
 */
public abstract class AbstracTcpMsgSender<T> implements TcpMsgSender<T> {

    @Override
    public void send(T msg) {
        IoSession session = getSession();
        if (session == null) {
            onSessionError(msg);
            throw new NullPointerException("不存在有效的TCP会话");
        }

        session.write(msg);
        onSessionSucc(msg);
    }

    public abstract IoSession getSession();
    public abstract void onSessionError(T msg);
    public abstract void onSessionSucc(T msg);
}
