package com.wangda.alarm.provider.bean.adaptor;

import com.google.common.base.Strings;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmLevel;
import com.wangda.alarm.service.bean.standard.protocol.StandardAlarmType;
import com.wangda.alarm.service.bean.vo.req.AlarmListReq;
import com.wangda.alarm.service.common.util.PageRequest;
import com.wangda.alarm.service.dao.req.QueryAlarmListParam;

/**
 * @author lixiaoxiong
 * @version 2017-11-07
 */
public class QueryAlarmListAdaptor {
    public static QueryAlarmListParam adaptToParam(AlarmListReq req) {
        QueryAlarmListParam param = new QueryAlarmListParam();
        param.setSegmentCode(req.getSegmentCode());
        param.setWorkShopCode(req.getWorkshopCode());
        param.setWorkAreaCode(req.getWorkareaCode());
        param.setStationCode(req.getStationCode());

        if (Strings.isNullOrEmpty(req.getLevel())) {
            param.setLevel(null);
        } else {
            param.setLevel(AlarmLevel.nameOf(req.getLevel()));
        }

        if (Strings.isNullOrEmpty(req.getAlarmType())) {
            param.setAlarmType(null);
        } else {
            param.setAlarmType(StandardAlarmType.nameOf(req.getAlarmType()).getCode());
        }

        param.setStartTime(req.getBeginDate());
        param.setEndTime(req.getEndDate());
        param.setRequest(new PageRequest(req.getCurrentPage(), req.getPageSize()));
        return param;
    }

}
