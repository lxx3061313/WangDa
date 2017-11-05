package com.wangda.alarm.service.impl;

import com.google.common.base.Strings;
import com.wangda.alarm.service.bean.biz.AlarmInfo;
import com.wangda.alarm.service.bean.biz.AlarmListInfo;
import com.wangda.alarm.service.bean.biz.DeptHierarchyInfo;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmContext;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmLevel;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.RespContext;
import com.wangda.alarm.service.bean.standard.protocol.StandardAlarmType;
import com.wangda.alarm.service.common.util.PageRequest;
import com.wangda.alarm.service.dao.AlarmInfoDao;
import com.wangda.alarm.service.dao.DeptInfoDao;
import com.wangda.alarm.service.dao.adaptor.AlarmInfoAdaptor;
import com.wangda.alarm.service.dao.po.AlarmInfoPo;
import com.wangda.alarm.service.dao.po.AlarmListPo;
import com.wangda.alarm.service.dao.req.QueryAlarmDetailParam;
import com.wangda.alarm.service.dao.req.QueryAlarmListParam;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
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

    public List<AlarmListInfo> queryAlarmListByParam(String segmentCode, String workshopCode,
            String workareaCode, String sourceCode, AlarmLevel level,
            StandardAlarmType type, PageRequest request) {
        QueryAlarmListParam param = new QueryAlarmListParam();
        param.setSegmentCode(segmentCode);
        param.setWorkShopCode(workshopCode);
        param.setWorkAreaCode(workareaCode);
        param.setStationCode(sourceCode);
        param.setLevel(level);
        param.setAlarmType(type == StandardAlarmType.ALLTYPE? null:type.getCode());
        List<AlarmListPo> alarmListPos = alarmInfoDao
                .queryAlarmByParam(param, new RowBounds(request.getOffset(), request.getLimit()));

        List<String> stationIds = alarmListPos.stream().map(AlarmListPo::getSourceTelecode).distinct()
                .collect(Collectors.toList());
        List<DeptHierarchyInfo> deptHierarchyInfos = deptInfoService
                .queryDeptHireraInfos(stationIds);
        Map<String, DeptHierarchyInfo> infoMap = deptHierarchyInfos.stream()
                .collect(Collectors.toMap(DeptHierarchyInfo::getStationSimpleName, p -> p));
        return AlarmInfoAdaptor
                .adaptToAlarmLists(alarmListPos, infoMap);
    }

    public List<AlarmInfo> queryAlarmDetailByParam(String segmentCode, String workshopCode,
            String workareaCode, String sourceCode, AlarmLevel level,
            StandardAlarmType type, String deviceName, PageRequest request) {
        //1. 构建查询参数
        QueryAlarmDetailParam param = new QueryAlarmDetailParam();
        param.setSegmentCode(segmentCode);
        param.setWorkShopCode(workshopCode);
        param.setWorkAreaCode(workareaCode);
        param.setStationCode(sourceCode);
        param.setAlarmType(type.getCode());
        param.setLevel(level);
        param.setDeviceName(deviceName);

        //2. 查询符合条件的参数
        List<AlarmInfoPo> infoPos = alarmInfoDao
                .queryAlarmDetail(param, new RowBounds(request.getOffset(), request.getLimit()));

        //3. 查询报警对应车站详情
        List<String> stationIds = infoPos.stream().map(AlarmInfoPo::getSourceTeleCode).distinct()
                .collect(Collectors.toList());
        List<DeptHierarchyInfo> deptHierarchyInfos = deptInfoService
                .queryDeptHireraInfos(stationIds);
        Map<String, DeptHierarchyInfo> infoMap = deptHierarchyInfos.stream()
                .collect(Collectors.toMap(DeptHierarchyInfo::getStationSimpleName, p -> p));

        //4. 转换为报警详情。
        return AlarmInfoAdaptor.adaptToAlarmInfos(infoPos, infoMap);
    }

    public int saveAlarmInfo(AlarmContext context) {
        DeptHierarchyInfo deptHierarchyInfo = deptInfoService
                .queryDeptHireraInfo(context.getHeader().getSourceTeleCode());
        AlarmInfoPo infoPo = AlarmInfoAdaptor.adaptToAlarmPo(context, deptHierarchyInfo);
        return alarmInfoDao.saveAlarmInfo(infoPo);
    }

    public List<AlarmInfo> queryAlarmByTimerange(Date from, Date to) {
        List<AlarmInfoPo> infoPos = alarmInfoDao.queryAlarmByTimeRange(from, to);
        if (CollectionUtils.isEmpty(infoPos)) {
            return Collections.EMPTY_LIST;
        }

        List<String> stationIds = infoPos.stream().map(AlarmInfoPo::getSourceTeleCode).distinct()
                .collect(Collectors.toList());
        List<DeptHierarchyInfo> deptHierarchyInfos = deptInfoService
                .queryDeptHireraInfos(stationIds);
        Map<String, DeptHierarchyInfo> infoMap = deptHierarchyInfos.stream()
                .collect(Collectors.toMap(DeptHierarchyInfo::getStationSimpleName, p -> p));
        return AlarmInfoAdaptor.adaptToAlarmInfos(infoPos, infoMap);
    }

    public List<AlarmInfo> queryAlarmByDeptAndLevel(String segment, String workshopCode, String workareaCode,
            List<AlarmLevel> levels, PageRequest pageRequest) {
        List<AlarmInfoPo> alarmInfoPos = alarmInfoDao
                .queryAlarmByDeptAndLevel(segment, workshopCode, workareaCode, levels,
                        new RowBounds(pageRequest.getOffset(), pageRequest.getLimit()));
        if (CollectionUtils.isEmpty(alarmInfoPos)) {
            return Collections.EMPTY_LIST;
        }

        List<String> stationIds = alarmInfoPos.stream().map(AlarmInfoPo::getSourceTeleCode).distinct()
                .collect(Collectors.toList());
        List<DeptHierarchyInfo> deptHierarchyInfos = deptInfoService
                .queryDeptHireraInfos(stationIds);
        Map<String, DeptHierarchyInfo> infoMap = deptHierarchyInfos.stream()
                .collect(Collectors.toMap(DeptHierarchyInfo::getStationSimpleName, p -> p));
        return AlarmInfoAdaptor.adaptToAlarmInfos(alarmInfoPos, infoMap);
    }

    public int countAlarmByDeptAndLevel(String segment, String workshopCode, String workareaCode,
            List<AlarmLevel> levels) {
        return alarmInfoDao.countAlarmByDeptAndLevel(segment, workshopCode, workareaCode, levels);
    }

    public int saveAlarmRespInfo(RespContext context) {
        DeptHierarchyInfo deptHierarchyInfo = deptInfoService
                .queryDeptHireraInfo(context.getHeader().getSourceTeleCode());
        List<AlarmInfoPo> alarmInfoPos = AlarmInfoAdaptor
                .adaptToAlarmPo(context, deptHierarchyInfo);
        return alarmInfoDao.saveAlarmInfos(alarmInfoPos);
    }
}
