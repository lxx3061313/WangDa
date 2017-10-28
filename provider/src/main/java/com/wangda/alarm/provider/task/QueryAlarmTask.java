package com.wangda.alarm.provider.task;

import com.wangda.alarm.provider.biz.QueryAlarmBiz;
import com.wangda.alarm.service.bean.standard.protocol.SegmentCode;
import com.wangda.alarm.service.impl.QueryAlarmParamBuilder;
import com.wangda.alarm.service.impl.QueryAlarmService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-28
 */
@Service
public class QueryAlarmTask {

    @Resource
    QueryAlarmBiz queryAlarmBiz;

    //todo 配置spring-task
    public void doTask() {
        //queryAlarmBiz.queryAlarm();
    }
}
