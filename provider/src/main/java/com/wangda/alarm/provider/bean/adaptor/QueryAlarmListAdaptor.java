package com.wangda.alarm.provider.bean.adaptor;

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
        param.setLevel(req.getLevel());
        param.setAlarmType(req.getAlarmType().getCode());
        param.setStartTime(req.getStartTime());
        param.setEndTime(req.getEndTime());
        param.setRequest(new PageRequest(req.getCurrentPage(), req.getPageSize()));
        return param;
    }

}
