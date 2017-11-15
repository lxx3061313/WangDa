package com.wangda.alarm.service.impl;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.wangda.alarm.service.bean.biz.AlarmInfo;
import com.wangda.alarm.service.bean.biz.AlarmListInfo;
import com.wangda.alarm.service.bean.biz.DeptHierarchyInfo;
import com.wangda.alarm.service.bean.biz.DeptInfo;
import com.wangda.alarm.service.bean.biz.RealTimeAlarmList;
import com.wangda.alarm.service.bean.biz.ShopAlarmMappingStatics;
import com.wangda.alarm.service.bean.standard.OverhaulType;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmContext;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmLevel;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmStatus;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.RespBody;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.RespContext;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.RespHeader;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.RespRecord;
import com.wangda.alarm.service.bean.standard.protocol.StandardAlarmType;
import com.wangda.alarm.service.common.util.ByteBufferUtil;
import com.wangda.alarm.service.common.util.PageRequest;
import com.wangda.alarm.service.dao.AlarmInfoDao;
import com.wangda.alarm.service.dao.adaptor.AlarmInfoAdaptor;
import com.wangda.alarm.service.dao.po.AlarmInfoPo;
import com.wangda.alarm.service.dao.po.AlarmListPo;
import com.wangda.alarm.service.dao.po.RealTimeAlarmListPo;
import com.wangda.alarm.service.dao.req.QueryAlarmDetailParam;
import com.wangda.alarm.service.dao.req.QueryAlarmListParam;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-25
 */
@Service
public class AlarmInfoService {

    private final static Logger logger = LoggerFactory.getLogger(AlarmInfoService.class);

    @Resource
    AlarmInfoDao alarmInfoDao;

    @Resource
    DeptInfoService deptInfoService;

    @Resource
    AlarmMsgPushService alarmMsgPushService;

    public List<AlarmListInfo> queryAlarmListByParam(QueryAlarmListParam param) {
        List<AlarmListPo> alarmListPos = alarmInfoDao
                .queryAlarmByParam(param, new RowBounds(param.getRequest().getOffset(),
                        param.getRequest().getLimit()));
        List<String> stationIds = alarmListPos.stream().map(AlarmListPo::getSourceTelecode)
                .distinct()
                .collect(Collectors.toList());
        List<DeptHierarchyInfo> deptHierarchyInfos = deptInfoService
                .queryDeptHireraInfos(stationIds);
        Map<String, DeptHierarchyInfo> infoMap = deptHierarchyInfos.stream()
                .collect(Collectors.toMap(DeptHierarchyInfo::getStationSimpleName, p -> p));
        return AlarmInfoAdaptor
                .adaptToAlarmLists(alarmListPos, infoMap);
    }

    public int countAlarmByParam(QueryAlarmListParam param) {
        return alarmInfoDao.countAlarmByParam(param);
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

    public int countAlarmDetail(String segmentCode, String workshopCode,
            String workareaCode, String sourceCode, AlarmLevel level,
            StandardAlarmType type, String deviceName) {
        //1. 构建查询参数
        QueryAlarmDetailParam param = new QueryAlarmDetailParam();
        param.setSegmentCode(segmentCode);
        param.setWorkShopCode(workshopCode);
        param.setWorkAreaCode(workareaCode);
        param.setStationCode(sourceCode);
        param.setAlarmType(type.getCode());
        param.setLevel(level);
        param.setDeviceName(deviceName);

        return alarmInfoDao.countAlarmDetail(param);
    }

    public int saveAlarmInfo(AlarmContext context) {
        DeptHierarchyInfo deptHierarchyInfo = deptInfoService
                .queryDeptHireraInfo(context.getHeader().getSourceTeleCode());
        AlarmInfoPo infoPo = AlarmInfoAdaptor.adaptToAlarmPo(context, deptHierarchyInfo);
        return alarmInfoDao.saveAlarmInfo(infoPo);
    }

    public void pushAppAlarmMsg(AlarmContext context) {
        DeptHierarchyInfo deptHierarchyInfo = deptInfoService
                .queryDeptHireraInfo(context.getHeader().getSourceTeleCode());
        AlarmInfoPo infoPo = AlarmInfoAdaptor.adaptToAlarmPo(context, deptHierarchyInfo);
        AlarmInfo alarmInfo = AlarmInfoAdaptor.adaptoAlarmInfo(infoPo, deptHierarchyInfo);
        alarmMsgPushService.push(deptHierarchyInfo, alarmInfo);
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

    public ShopAlarmMappingStatics querySegmentAlarmCount(String segmentCode, Date from, Date to) {
        List<AlarmInfoPo> infoPos = alarmInfoDao
                .queryAlarmBySegmentInTimeRange(segmentCode, from, to);

        ShopAlarmMappingStatics statics = new ShopAlarmMappingStatics();
        //1. 按级别&车间统计
        Multimap<AlarmLevel, AlarmInfoPo> levelMap = HashMultimap.create();
        Multimap<String, AlarmInfoPo> shopMap = HashMultimap.create();
        for (AlarmInfoPo po : infoPos) {
            levelMap.put(po.getAlarmLevel(), po);
            shopMap.put(po.getWorkshopCode(), po);
        }

        //2. 统计报警级别数据
        Map<AlarmLevel, Integer> levelIntegerMap = new HashMap<>();
        levelIntegerMap.put(AlarmLevel.LEVEL_ONE, levelMap.get(AlarmLevel.LEVEL_ONE) == null ?
                0 : levelMap.get(AlarmLevel.LEVEL_ONE).size());
        levelIntegerMap.put(AlarmLevel.LEVEL_TWO, levelMap.get(AlarmLevel.LEVEL_TWO) == null ?
                0 : levelMap.get(AlarmLevel.LEVEL_TWO).size());
        levelIntegerMap.put(AlarmLevel.LEVEL_THREE, levelMap.get(AlarmLevel.LEVEL_THREE) == null ?
                0 : levelMap.get(AlarmLevel.LEVEL_THREE).size());
        levelIntegerMap.put(AlarmLevel.WARN, levelMap.get(AlarmLevel.WARN) == null ?
                0 : levelMap.get(AlarmLevel.WARN).size());
        statics.setAlarmLevelStatics(levelIntegerMap);


        //3. 统计车间统计数据
        List<String> shopsCode = shopMap.values().stream().map(AlarmInfoPo::getWorkshopCode)
                .collect(Collectors.toList());
        List<DeptInfo> shopsDeptInfo = deptInfoService.queryDeptInfosByCodes(shopsCode);
        Map<String, DeptInfo> shotDeptMap = shopsDeptInfo.stream()
                .collect(Collectors.toMap(DeptInfo::getDeptSimplename, p->p));
        statics.setDeptInfoMap(shotDeptMap);


        Map<String, Integer> shopAlarmCount = new HashMap<>();
        for (String key : shopMap.keySet()) {
            Collection<AlarmInfoPo> alarmInfoPos = shopMap.get(key);
            int count = 0;
            if (CollectionUtils.isNotEmpty(alarmInfoPos)) {
                count = alarmInfoPos.size();
            }
            shopAlarmCount.put(key, count);
        }
        statics.setSegmentCode(segmentCode);
        statics.setAlarmShopStatics(shopAlarmCount);
        return statics;
    }

    public List<RealTimeAlarmList> queryAlarmByDeptAndLevel(String segment, String workshopCode,
            String workareaCode, String stationCode,
            List<AlarmLevel> levels, PageRequest pageRequest) {

        // 1.报警信息
        List<RealTimeAlarmListPo> alarmListPos = alarmInfoDao.queryRealtimeAlarmList(segment,
                workshopCode, workareaCode, stationCode, levels,
                new RowBounds(pageRequest.getOffset(), pageRequest.getLimit()));
        if (CollectionUtils.isEmpty(alarmListPos)) {
            return Collections.EMPTY_LIST;
        }

        //2. 车站信息
        List<String> stationCodes = alarmListPos.stream().map(RealTimeAlarmListPo::getStationCode)
                .collect(Collectors.toList());
        List<DeptInfo> deptInfos = deptInfoService.queryDeptInfosByCodes(stationCodes);
        Map<String, String> stationNameMap = deptInfos.stream()
                .collect(Collectors.toMap(DeptInfo::getDeptSimplename,
                        DeptInfo::getDeptFullname));
        return AlarmInfoAdaptor.adaptToRealTimeAlarm(alarmListPos, stationNameMap);
    }

    public int countRealTimeAlarmList(String segment, String workshopCode, String workareaCode,
            String stationCode,
            List<AlarmLevel> levels) {
        return alarmInfoDao
                .countRealtimeAlarmList(segment, workshopCode, workareaCode, stationCode, levels);
    }

    public int saveAlarmRespInfo(RespContext context) {
        RespHeader header = context.getHeader();
        RespBody body = context.getBody();
        List<AlarmInfoPo> result = new ArrayList<>();
        List<RespRecord> respRecords = body.getRespRecords();
        for (RespRecord respRecord : respRecords) {
            DeptHierarchyInfo hinfo = deptInfoService
                    .queryDeptHireraInfo(respRecord.getStationCode());
            if (hinfo == null) {
                logger.error("can not find dept{}", respRecord.getStationCode());
                continue;
            }
            AlarmInfoPo po = new AlarmInfoPo();
            po.setSegment(hinfo.getSegmentSimpleName());
            po.setWorkshopCode(hinfo.getWorkShopSimpleName());
            po.setWorkAreaCode(hinfo.getWorkAreaSimpleName());
            po.setTargetTeleCode(header.getTargetTeleCode());
            po.setSourceTeleCode(hinfo.getStationSimpleName());
            po.setAlarmLevel(respRecord.getLevel());
            po.setDeviceType(ByteBufferUtil.bytesToShort(respRecord.getDeviceType()));
            po.setDeviceNo(ByteBufferUtil.bytesToShort(respRecord.getDeviceNo()));
            po.setDeviceName(respRecord.getDeviceName() == null ? "" : respRecord.getDeviceName());
            po.setAlarmTime(respRecord.getHappenTime());
            po.setRecoverTime(respRecord.getRecoverTime());
            po.setStatus(AlarmStatus.ALARM);
            po.setAlarmContext(respRecord.getAlarmCtx());
            po.setOverhaulFlag(OverhaulType.OTHER);
            po.setCreateTime(new Date());
            po.setRemark("");
            po.setAlarmType(ByteBufferUtil
                    .bytesToShort(ByteBufferUtil.subBytes(respRecord.getReserveField(), (byte) 2)));
            result.add(po);
        }
        return alarmInfoDao.saveAlarmInfos(result);
    }
}
