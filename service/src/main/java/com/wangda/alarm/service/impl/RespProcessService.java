package com.wangda.alarm.service.impl;

import com.wangda.alarm.service.bean.standard.alarminfo.resp.RespContext;
import com.wangda.alarm.service.common.message.AbstractMessageProcessor;
import com.wangda.alarm.service.common.util.json.JsonUtil;
import javax.annotation.Resource;
import org.apache.mina.core.session.IoSession;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-29
 */
@Service
public class RespProcessService extends AbstractMessageProcessor<RespContext> {
    private final static Logger logger = LoggerFactory.getLogger(RespProcessService.class);

    @Resource
    AlarmInfoService alarmInfoService;

    @Override
    public void processInterval(IoSession session, RespContext message) {
        logger.info("receive resp data, {}", JsonUtil.of(message));
        alarmInfoService.saveAlarmRespInfo(message);
    }

    @Override
    public Class messageType() {
        return RespContext.class;
    }
}
