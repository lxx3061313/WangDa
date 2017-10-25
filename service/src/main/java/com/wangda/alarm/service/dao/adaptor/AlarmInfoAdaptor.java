package com.wangda.alarm.service.dao.adaptor;

import com.wangda.alarm.service.bean.biz.AlarmInfo;
import com.wangda.alarm.service.dao.po.AlarmInfoPo;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;

/**
 * @author lixiaoxiong
 * @version 2017-10-25
 */
public class AlarmInfoAdaptor {
    public static AlarmInfo adaptoAlarmInfo(AlarmInfoPo po)  {
        AlarmInfo alarmInfo = new AlarmInfo();
        alarmInfo.setStationCode(po.getSourceTeleCode());
        alarmInfo.setStationName("");
        alarmInfo.setSegmentCode(po.getSegment());
        alarmInfo.setSegmentName("");
        alarmInfo.setAlarmLevel(po.getAlarmLevel());
        alarmInfo.setDeviceNo(po.getDeviceNo());
        alarmInfo.setDeviceName(po.getDeviceName());
        alarmInfo.setAlarmContext(po.getAlarmContext());
        alarmInfo.setAlarmTime(po.getAlarmTime());
        alarmInfo.setRecoverTime(po.getRecoverTime());
        alarmInfo.setRepeatCount(po.getRepeatCount());
        alarmInfo.setStatus(po.getStatus());
        return alarmInfo;
    }

    public static List<AlarmInfo> adaptToAlarmInfos(List<AlarmInfoPo> pos) {
        if (CollectionUtils.isEmpty(pos))
            return Collections.EMPTY_LIST;
        return pos.stream().map(AlarmInfoAdaptor::adaptoAlarmInfo).collect(Collectors.toList());
    }
}
