package com.wangda.alarm.provider.bean.adaptor;

import com.wangda.alarm.provider.bean.AlarmOutlineVo;
import com.wangda.alarm.service.bean.biz.AlarmListInfo;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;

/**
 * @author lixiaoxiong
 * @version 2017-11-02
 */
public class AlarmVoAdaptor {
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
        vo.setAlarmCount(0);
        vo.setAlarmLevel(info.getAlarmLevel().getDesc());
        vo.setAlarmContext("");
        return vo;
    }

    public static List<AlarmOutlineVo> adaptOutlineVos(List<AlarmListInfo> infos) {
        if (CollectionUtils.isEmpty(infos))
            return Collections.EMPTY_LIST;
        return infos.stream().map(AlarmVoAdaptor::adaptOutlineVo).collect(Collectors.toList());
    }
}
