package com.wangda.alarm.service.common.message;

import javax.annotation.Resource;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-28
 */
@Service
public class MessageDispatcher {

    @Resource
    MessageServiceRegCenter messageServiceRegCenter;

    @SuppressWarnings("unchecked")
    public void dispatch(IoSession session, Object message) {
        messageServiceRegCenter
                .getProcessor(message.getClass())
                .process(session, message);
    }
}
