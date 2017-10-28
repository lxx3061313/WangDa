package com.wangda.alarm.service.impl;

import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmContext;
import com.wangda.alarm.service.common.message.AbstractMessageProcessor;
import org.apache.mina.core.session.IoSession;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-28
 */
@Service
public class AlarmProcessService extends AbstractMessageProcessor<AlarmContext>{

    private final Logger logger = LoggerFactory.getLogger(AlarmProcessService.class);
    @Override
    public void processInterval(IoSession session, AlarmContext message) {
        //SAVE
        logger.info("message save to the db");
    }

    @Override
    public Class messageType() {
        return AlarmContext.class;
    }
}
