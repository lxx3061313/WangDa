package com.wangda.alarm.service.impl;

import com.wangda.alarm.service.bean.standard.alarminfo.resp.QueryAlarmParam;
import com.wangda.alarm.service.common.tcplayer.AbstracTcpMsgSender;
import com.wangda.alarm.service.common.tcplayer.ServerHandler;
import javax.annotation.Resource;
import org.apache.mina.core.session.IoSession;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-28
 */
@Service
public class QueryAlarmService extends AbstracTcpMsgSender<QueryAlarmParam>{
    private final static Logger logger = LoggerFactory.getLogger(QueryAlarmService.class);

    @Resource
    ServerHandler serverHandler;

    @Override
    protected IoSession getSession() {
        return serverHandler.getSession();
    }

    @Override
    protected void onSessionError(QueryAlarmParam msg) {
        logger.error("发送查询历史报警命令失败");
    }

    @Override
    protected void onSessionSucc(QueryAlarmParam msg) {

    }
}
