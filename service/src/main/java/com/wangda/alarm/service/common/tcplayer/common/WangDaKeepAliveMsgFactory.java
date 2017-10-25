package com.wangda.alarm.service.common.tcplayer.common;

import com.wangda.alarm.service.bean.standard.constant.KeepAliveMsg;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-25
 */
@Service
public class WangDaKeepAliveMsgFactory implements KeepAliveMessageFactory {

    @Override
    public boolean isRequest(IoSession session, Object message) {
        byte [] temp = (byte []) message;
        return KeepAliveMsg.isKeepAliveMsg(temp);
    }

    @Override
    public boolean isResponse(IoSession session, Object message) {
        byte [] temp = (byte []) message;
        return KeepAliveMsg.isKeepAliveMsg(temp);
    }

    @Override
    public Object getRequest(IoSession session) {
        return KeepAliveMsg.msg();
    }

    @Override
    public Object getResponse(IoSession session, Object request) {
        return KeepAliveMsg.msg();
    }
}