package com.wangda.alarm.provider.controller;

import com.wangda.alarm.provider.bean.AlarmOutlineVo;
import com.wangda.alarm.service.bean.vo.AlarmStatisticsVo;
import com.wangda.alarm.service.bean.vo.RealTimeAlarmVo;
import com.wangda.alarm.service.bean.vo.req.AlarmListReq;
import com.wangda.alarm.service.impl.AlarmInfoService;
import com.wangda.alarm.service.common.springconfig.annotation.JsonBody;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lixiaoxiong
 * @version 2017-10-25
 */
@Controller
@RequestMapping("/wangda/mobile/alarm")
public class AlarmInfoController {

    @Resource
    AlarmInfoService alarmInfoService;

    @RequestMapping("/list")
    @JsonBody
    public List<AlarmOutlineVo> queryAlarmList(@RequestBody AlarmListReq req) {
        return alarmInfoService.queryAlarmsBySegAndLev();
    }

    @RequestMapping("/statis")
    @JsonBody
    public AlarmStatisticsVo queryAlarmStatistics() {
        return null;
    }

    @RequestMapping("/realAlarm")
    @JsonBody
    public RealTimeAlarmVo queryRealTimeAlarm() {
        return null;
    }
}
