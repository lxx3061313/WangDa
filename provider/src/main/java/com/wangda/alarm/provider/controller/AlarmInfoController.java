package com.wangda.alarm.provider.controller;

import com.wangda.alarm.provider.bean.AlarmDetailVo;
import com.wangda.alarm.provider.bean.AlarmListResp;
import com.wangda.alarm.provider.bean.AlarmOutlineVo;
import com.wangda.alarm.provider.biz.AlarmInfoBiz;
import com.wangda.alarm.service.bean.vo.AlarmStatisticsVo;
import com.wangda.alarm.service.bean.vo.RealTimeAlarmVo;
import com.wangda.alarm.service.bean.vo.req.AlarmDetailReq;
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
    AlarmInfoBiz alarmInfoBiz;

    @RequestMapping("/list")
    @JsonBody
    public AlarmListResp queryAlarmList(@RequestBody AlarmListReq req) {
        List<AlarmOutlineVo> outlineVos = alarmInfoBiz.queryAlarmList(req);
        return null;
    }

    @RequestMapping("/detail")
    @JsonBody
    public List<AlarmDetailVo> queryAlarmDetail(@RequestBody AlarmDetailReq req) {
        return alarmInfoBiz.queryAlarmDetail(req);
    }


    @RequestMapping("/statis")
    @JsonBody
    public AlarmStatisticsVo queryAlarmStatistics() {
        return alarmInfoBiz.queryAlarmStatic();
    }

    @RequestMapping("/realAlarm")
    @JsonBody
    public RealTimeAlarmVo queryRealTimeAlarm() {
        return null;
    }
}
