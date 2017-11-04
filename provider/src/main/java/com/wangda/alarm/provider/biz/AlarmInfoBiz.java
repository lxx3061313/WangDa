package com.wangda.alarm.provider.biz;

import com.wangda.alarm.provider.bean.AlarmDetailVo;
import com.wangda.alarm.provider.bean.AlarmOutlineVo;
import com.wangda.alarm.provider.bean.adaptor.AlarmVoAdaptor;
import com.wangda.alarm.service.bean.biz.AlarmInfo;
import com.wangda.alarm.service.bean.biz.AlarmListInfo;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmLevel;
import com.wangda.alarm.service.bean.standard.protocol.SegmentCode;
import com.wangda.alarm.service.bean.standard.protocol.StandardAlarmType;
import com.wangda.alarm.service.bean.vo.AlarmStatisticsVo;
import com.wangda.alarm.service.bean.vo.req.AlarmDetailReq;
import com.wangda.alarm.service.bean.vo.req.AlarmListReq;
import com.wangda.alarm.service.common.util.PageRequest;
import com.wangda.alarm.service.impl.AlarmInfoService;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-26
 */
@Service
public class AlarmInfoBiz {

    private final static int ALARM_DATA_TIME_BEFORE = 36;

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


    /**
     * 查询36小时内的报警数据
     * @return AlarmStatisticsVo
     */
    public AlarmStatisticsVo queryAlarmStatic() {
        List<AlarmInfo> infos = alarmInfoService
                .queryAlarmByTimerange(beforeHoursFromNow(ALARM_DATA_TIME_BEFORE),
                        new Date());

        AlarmStatisticsVo vo = new AlarmStatisticsVo();
        // 一级报警个数
        vo.setLevelOneCount(infos.stream().filter(p->p.getAlarmLevel() == AlarmLevel.LEVEL_ONE).count());

        // 二级报警个数
        vo.setLeveTowCount(infos.stream().filter(p->p.getAlarmLevel() == AlarmLevel.LEVEL_TWO).count());

        // 三级报警个数
        vo.setLeveThreeCount(infos.stream().filter(p->p.getAlarmLevel() == AlarmLevel.LEVEL_THREE).count());

        // 预警个数
        vo.setWarnCount(infos.stream().filter(p->p.getAlarmLevel() == AlarmLevel.WARN).count());

        // 成都个数
        vo.setCdCount(infos.stream().filter(p->p.getSegmentCode()
                .equals(SegmentCode.CDD.getCode())).count());

        // 重庆个数
        vo.setCqCount(infos.stream().filter(p->p.getSegmentCode()
                .equals(SegmentCode.CQD.getCode())).count());

        // 贵阳个数
        vo.setGyCount(infos.stream().filter(p->p.getSegmentCode()
                .equals(SegmentCode.GYD.getCode())).count());

        // 达州个数
        vo.setDzCount(infos.stream().filter(p->p.getSegmentCode()
                .equals(SegmentCode.DZD.getCode())).count());

        // 贵阳北个数
        vo.setGybCount(infos.stream().filter(p->p.getSegmentCode()
                .equals(SegmentCode.GYBD.getCode())).count());
        return vo;
    }

    private Date beforeHoursFromNow(int beforeHours) {
        return DateUtils.addHours(new Date(), beforeHours);
    }
}
