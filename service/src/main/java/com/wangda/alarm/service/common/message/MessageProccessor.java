package com.wangda.alarm.service.common.message;

import org.apache.mina.core.service.IoService;
import org.apache.mina.core.session.IoSession;

/**
 * @author lixiaoxiong
 * @version 2017-10-28
 */
public interface MessageProccessor<T> {
    void process(IoSession session, T message);
}
