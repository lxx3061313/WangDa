package com.wangda.alarm.service.impl;

import com.google.common.base.Strings;
import com.wangda.alarm.service.bean.biz.AlarmInfo;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmLevel;
import com.wangda.alarm.service.dao.AlarmInfoDao;
import com.wangda.alarm.service.dao.adaptor.AlarmInfoAdaptor;
import com.wangda.alarm.service.dao.po.AlarmInfoPo;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-25
 */
@Service
public class AlarmInfoService {

    @Resource
    AlarmInfoDao alarmInfoDao;

    public List<AlarmInfo> queryAlarmsBySegAndLev(String segmentCode, AlarmLevel level) {
        if (Strings.isNullOrEmpty(segmentCode) && level == null) {
            return Collections.EMPTY_LIST;
        }
        List<AlarmInfoPo> alarmInfoPos = alarmInfoDao.queryAlarmBySegAndLev(segmentCode, level);
        return AlarmInfoAdaptor.adaptToAlarmInfos(alarmInfoPos);
    }
}
