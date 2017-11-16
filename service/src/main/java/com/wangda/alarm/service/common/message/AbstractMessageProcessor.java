package com.wangda.alarm.service.common.message;

import com.wangda.alarm.service.bean.standard.WangDaBizBean;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.apache.mina.core.session.IoSession;

/**
 * @author wangshuo
 * @version 2017-10-28
 */
public abstract class AbstractMessageProcessor<T> implements MessageProccessor<T> {

    @Resource
    MessageServiceRegCenter messageServiceRegCenter;

    @Override
    public void process(IoSession session, T message) {
        this.processInterval(session, message);
    }

    public abstract void processInterval(IoSession session, T message);
    public abstract Class messageType();

    @PostConstruct
    public void regService(){
        messageServiceRegCenter.reg(messageType(), this);
    }
}
