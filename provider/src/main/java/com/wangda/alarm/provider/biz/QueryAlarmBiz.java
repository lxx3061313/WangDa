package com.wangda.alarm.provider.biz;

import com.wangda.alarm.service.bean.vo.req.AlarmListReq;
import com.wangda.alarm.service.impl.QueryAlarmParamBuilder;
import com.wangda.alarm.service.impl.QueryAlarmService;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author zhangxin
 * @version 2017-10-28
 */
@Service
public class QueryAlarmBiz {

    @Resource
    QueryAlarmService queryAlarmService;

    /**
     * 向平台发送请求指令, 等待回调结果
     * @param segmentCode 段code
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    public void queryAlarm(String segmentCode, Date startTime, Date endTime) {
        queryAlarmService.send(QueryAlarmParamBuilder
                .builder()
                .addSegmentCode(segmentCode)
                .addStartTime(startTime)
                .addEndTime(endTime)
                .build());
    }


    public void queryAlarmList(AlarmListReq req) {

    }
}
