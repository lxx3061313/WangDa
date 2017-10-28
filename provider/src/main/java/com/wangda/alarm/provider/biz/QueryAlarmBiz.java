package com.wangda.alarm.provider.biz;

import com.wangda.alarm.service.impl.QueryAlarmParamBuilder;
import com.wangda.alarm.service.impl.QueryAlarmService;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-28
 */
@Service
public class QueryAlarmBiz {

    @Resource
    QueryAlarmService queryAlarmService;

    public void queryAlarm(String segmentCode, Date startTime, Date endTime) {
        queryAlarmService.send(QueryAlarmParamBuilder
                .builder()
                .addSegmentCode(segmentCode)
                .addStartTime(startTime)
                .addEndTime(endTime)
                .build());
    }
}
