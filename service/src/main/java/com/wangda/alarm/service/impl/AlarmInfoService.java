package com.wangda.alarm.service.impl;

import com.google.common.base.Strings;
import com.wangda.alarm.service.bean.biz.AlarmInfo;
import com.wangda.alarm.service.bean.biz.DeptHierarchyInfo;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmContext;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmLevel;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.RespContext;
import com.wangda.alarm.service.dao.AlarmInfoDao;
import com.wangda.alarm.service.dao.DeptInfoDao;
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
public class AlarmInfoService{

    @Resource
    AlarmInfoDao alarmInfoDao;

    @Resource
    DeptInfoService deptInfoService;

    public List<AlarmInfo> queryAlarmsBySegAndLev(String segmentCode, AlarmLevel level) {
        if (Strings.isNullOrEmpty(segmentCode) && level == null) {
            return Collections.EMPTY_LIST;
        }
        List<AlarmInfoPo> alarmInfoPos = alarmInfoDao.queryAlarmBySegAndLev(segmentCode, level);
        return AlarmInfoAdaptor.adaptToAlarmInfos(alarmInfoPos);
    }

    public int saveAlarmInfo(AlarmContext context) {
        DeptHierarchyInfo deptHierarchyInfo = deptInfoService
                .queryDeptHireraInfo(context.getHeader().getSourceTeleCode());
        AlarmInfoPo infoPo = AlarmInfoAdaptor.adaptToAlarmPo(context, deptHierarchyInfo);
        return alarmInfoDao.saveAlarmInfo(infoPo);
    }

    public int saveAlarmRespInfo(RespContext context) {
        DeptHierarchyInfo deptHierarchyInfo = deptInfoService
                .queryDeptHireraInfo(context.getHeader().getSourceTeleCode());
        List<AlarmInfoPo> alarmInfoPos = AlarmInfoAdaptor
                .adaptToAlarmPo(context, deptHierarchyInfo);
        return alarmInfoDao.saveAlarmInfos(alarmInfoPos);
    }
}
