package com.wangda.alarm.provider.bean.adaptor;

import com.wangda.alarm.provider.bean.AlarmDetailVo;
import com.wangda.alarm.provider.bean.AlarmOutlineVo;
import com.wangda.alarm.service.bean.biz.AlarmInfo;
import com.wangda.alarm.service.bean.biz.AlarmListInfo;
import com.wangda.alarm.service.bean.biz.RealTimeAlarmList;
import com.wangda.alarm.service.bean.standard.protocol.StandardAlarmType;
import com.wangda.alarm.service.bean.vo.RealTimeAlarmItem;
import com.wangda.alarm.service.bean.vo.RealTimeAlarmVo;
import com.wangda.alarm.service.common.util.DateFormatUtil;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


/**
 * @author lixiaoxiong
 * @version 2017-11-02
 */
public class AlarmVoAdaptor {
    private final static Logger logger = LoggerFactory.getLogger(AlarmVoAdaptor.class);

    public static AlarmOutlineVo adaptOutlineVo(AlarmListInfo info) {
        AlarmOutlineVo vo = new AlarmOutlineVo();
        vo.setSegmentCode(info.getSegment());
        vo.setSegmentName(info.getSegmentName());
        vo.setWorkshopCode(info.getWorkshopCode());
        vo.setWorkshopName(info.getWorkshopName());
        vo.setWorkareaCode(info.getWorkareaCode());
        vo.setWorkareaName(info.getWorkareaName());
        vo.setStationCode(info.getStationCode());
        vo.setStationName(info.getStationName());
        vo.setDeviceName(info.getDeviceName());
        vo.setAlarmCount(info.getAlarmCount());
        vo.setAlarmLevel(info.getAlarmLevel().name());
        StandardAlarmType alarmType = StandardAlarmType.codeOf(info.getAlarmType());
        if (alarmType == null) {
            logger.error("报警类型未知, 报警类型编码:{}", info.getAlarmType());
            alarmType = StandardAlarmType.UNKNOW;
        }
        vo.setAlarmType(alarmType.name());
        return vo;
    }

    public static AlarmDetailVo adaptDetailVo(AlarmInfo info) {
        AlarmDetailVo vo = new AlarmDetailVo();
        vo.setSegmentName(info.getSegmentName());
        vo.setWorkshopName(info.getWorkshopName());
        vo.setWorkareaName(info.getWorkAreaName());
        vo.setStationName(info.getStationName());
        vo.setAlarmContext(info.getAlarmContext());
        vo.setAlarmLevel(info.getAlarmLevel().getDesc());
        vo.setAlarmTime(DateFormatUtil.format4y2M2d2h2m(info.getAlarmTime()));
        vo.setRecoverTime(info.getRecoverTime() == null ?
                "" : DateFormatUtil.format4y2M2d2h2m(info.getRecoverTime()));
        return vo;
    }

    public static List<AlarmDetailVo> adaptDetailVos(List<AlarmInfo> infos) {
        if (CollectionUtils.isEmpty(infos)) {
            return Collections.EMPTY_LIST;
        }
        return infos.stream().map(AlarmVoAdaptor::adaptDetailVo).collect(Collectors.toList());
    }

    public static List<AlarmOutlineVo> adaptOutlineVos(List<AlarmListInfo> infos) {
        if (CollectionUtils.isEmpty(infos)) {
            return Collections.EMPTY_LIST;
        }
        return infos.stream().map(AlarmVoAdaptor::adaptOutlineVo).collect(Collectors.toList());
    }

    public static RealTimeAlarmItem adaptToRealTimeItem(RealTimeAlarmList info) {
        RealTimeAlarmItem item = new RealTimeAlarmItem();
        item.setHeadInfo(info.getAlarmContext());
        item.setContext(
                "报警车站:" + info.getStationName() + " 设备名称:" + info.getDeviceName() + " 报警次数:" + info
                        .getAlarmCount());
        item.setSegmentCode(info.getSegmentCode());
        item.setWorkshopCode(info.getWorkshopCode());
        item.setWorkareaCode(info.getWorkareaCode());
        item.setStationCode(info.getStationCode());
        item.setAlarmType(info.getAlarmType().name());
        item.setAlarmLevel(info.getAlarmLevel());
        item.setDeviceName(info.getDeviceName());
        return item;
    }

    public static RealTimeAlarmVo adaptRealAlarmVo(List<RealTimeAlarmList> infos, int totalCount) {
        RealTimeAlarmVo vo = new RealTimeAlarmVo();
        vo.setTotalCount(totalCount);
        if (CollectionUtils.isEmpty(infos)) {
            vo.setAlarms(Collections.EMPTY_LIST);
        } else {
            List<RealTimeAlarmItem> collect = infos.stream()
                    .map(AlarmVoAdaptor::adaptToRealTimeItem)
                    .collect(Collectors.toList());
            vo.setAlarms(collect);
        }
        return vo;
    }
}
