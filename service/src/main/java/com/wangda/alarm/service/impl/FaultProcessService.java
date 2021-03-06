package com.wangda.alarm.service.impl;

import com.wangda.alarm.service.bean.standard.alarminfo.fault.FaultContext;
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
public class FaultProcessService extends AbstractMessageProcessor<FaultContext> {
    private final static Logger logger = LoggerFactory.getLogger(FaultProcessService.class);

    @Resource
    FaultIInfoService faultIInfoService;

    @Override
    public void processInterval(IoSession session, FaultContext message) {
        logger.info("receive fault data, {}", JsonUtil.of(message));
        faultIInfoService.saveFaultInfo(message);
    }

    @Override
    public Class messageType() {
        return FaultContext.class;
    }
}
