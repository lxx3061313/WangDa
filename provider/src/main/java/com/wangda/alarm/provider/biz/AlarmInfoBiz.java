package com.wangda.alarm.provider.biz;

import com.wangda.alarm.provider.bean.AlarmDetailVo;
import com.wangda.alarm.provider.bean.AlarmOutlineVo;
import com.wangda.alarm.provider.bean.adaptor.AlarmVoAdaptor;
import com.wangda.alarm.service.bean.biz.AlarmInfo;
import com.wangda.alarm.service.bean.biz.AlarmListInfo;
import com.wangda.alarm.service.bean.standard.protocol.StandardAlarmType;
import com.wangda.alarm.service.bean.vo.req.AlarmDetailReq;
import com.wangda.alarm.service.bean.vo.req.AlarmListReq;
import com.wangda.alarm.service.common.util.PageRequest;
import com.wangda.alarm.service.impl.AlarmInfoService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-26
 */
@Service
public class AlarmInfoBiz {

    @Resource
    AlarmInfoService alarmInfoService;

    public List<AlarmOutlineVo> queryAlarmList(AlarmListReq listReq) {
        List<AlarmListInfo> listInfos = alarmInfoService
                .queryAlarmListByParam(listReq.getSegmentCode(), listReq.getWorkshopCode(),
                        listReq.getWorkareaCode(), listReq.getStationCode(), listReq.getLevel(),
                        StandardAlarmType.nameOf(listReq.getAlarmType()),
                        new PageRequest(listReq.getCurrentSize(), listReq.getPageSize()));
        return AlarmVoAdaptor.adaptOutlineVos(listInfos);
    }

    public List<AlarmDetailVo> queryAlarmDetail(AlarmDetailReq req) {
        List<AlarmInfo> infos = alarmInfoService
                .queryAlarmDetailByParam(req.getSegmentCode(), req.getWorkshopCode(),
                        req.getWorkareaCode(), req.getStationCode(), req.getLevel(),
                        req.getAlarmType()
                        , req.getDeviceName(),
                        new PageRequest(req.getCurrentPage(), req.getPageSize()));
        return AlarmVoAdaptor.adaptDetailVos(infos);
    }
}
