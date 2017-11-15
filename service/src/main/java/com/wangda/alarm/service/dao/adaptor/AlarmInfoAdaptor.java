package com.wangda.alarm.service.dao.adaptor;

import com.wangda.alarm.service.bean.biz.AlarmInfo;
import com.wangda.alarm.service.bean.biz.AlarmListInfo;
import com.wangda.alarm.service.bean.biz.DeptHierarchyInfo;
import com.wangda.alarm.service.bean.biz.RealTimeAlarmList;
import com.wangda.alarm.service.bean.standard.OverhaulType;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmBody;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmContext;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmHeader;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmStatus;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.RespBody;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.RespContext;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.RespHeader;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.RespRecord;
import com.wangda.alarm.service.bean.standard.protocol.StandardAlarmType;
import com.wangda.alarm.service.common.util.ByteBufferUtil;
import com.wangda.alarm.service.dao.po.AlarmExtInfoPo;
import com.wangda.alarm.service.dao.po.AlarmInfoPo;
import com.wangda.alarm.service.dao.po.AlarmListPo;
import com.wangda.alarm.service.dao.po.RealTimeAlarmListPo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;

/**
 * @author lixiaoxiong
 * @version 2017-10-25
 */
public class AlarmInfoAdaptor {
    public static AlarmInfo adaptoAlarmInfo(AlarmInfoPo po, DeptHierarchyInfo info)  {
        AlarmInfo alarmInfo = new AlarmInfo();
        alarmInfo.setStationCode(po.getSourceTeleCode());
        alarmInfo.setStationName(info.getStationName());
        alarmInfo.setSegmentCode(po.getSegment());
        alarmInfo.setSegmentName(info.getSegment());
        alarmInfo.setWorkshopCode(po.getWorkshopCode());
        alarmInfo.setWorkshopName(info.getWorkShopName());
        alarmInfo.setWorkAreaCode(po.getWorkAreaCode());
        alarmInfo.setWorkAreaName(info.getWorkAreaName());
        alarmInfo.setAlarmLevel(po.getAlarmLevel());
        alarmInfo.setDeviceNo(po.getDeviceNo());
        alarmInfo.setDeviceName(po.getDeviceName());
        alarmInfo.setAlarmContext(po.getAlarmContext());
        alarmInfo.setAlarmTime(po.getAlarmTime());
        alarmInfo.setRecoverTime(po.getRecoverTime());
        alarmInfo.setStatus(po.getStatus());
        alarmInfo.setAlarmType(po.getAlarmType());
        alarmInfo.setRepeatCount(0);
        return alarmInfo;
    }

    public static AlarmInfoPo adaptToAlarmPo(AlarmContext context, DeptHierarchyInfo hinfo) {
        AlarmInfoPo po = new AlarmInfoPo();
        po.setSegment(hinfo.getSegmentSimpleName());
        po.setWorkshopCode(hinfo.getWorkShopSimpleName());
        po.setWorkAreaCode(hinfo.getWorkAreaSimpleName());

        AlarmHeader header = context.getHeader();
        po.setTargetTeleCode(header.getTargetTeleCode());
        po.setSourceTeleCode(header.getSourceTeleCode());

        AlarmBody body = context.getBody();
        po.setAlarmType(ByteBufferUtil.bytesToShort(body.getAlartType()));
        po.setAlarmLevel(body.getAlartLevel());
        po.setDeviceType(ByteBufferUtil.bytesToShort(body.getDeviceType()));
        po.setDeviceNo(ByteBufferUtil.bytesToShort(body.getDeviceNo()));
        po.setDeviceName(body.getDeviceName());
        po.setAlarmTime(body.getAlarmTime());
        po.setRecoverTime(body.getRecoverTime());
        po.setStatus(body.getStatus());
        po.setAlarmContext(body.getAlarmCtx());
        po.setOverhaulFlag(body.getOverhaulType());
        po.setCreateTime(new Date());
        po.setRemark("");
        po.setExtInfo(new AlarmExtInfoPo());
        return po;
    }

    public static AlarmListInfo adaptToAlarmList(AlarmListPo po, DeptHierarchyInfo info) {
        AlarmListInfo listInfo = new AlarmListInfo();
        listInfo.setSegment(info.getSegmentSimpleName());
        listInfo.setSegmentName(info.getSegment());
        listInfo.setWorkshopCode(info.getWorkShopSimpleName());
        listInfo.setWorkshopName(info.getWorkShopName());
        listInfo.setWorkareaCode(info.getWorkAreaSimpleName());
        listInfo.setWorkareaName(info.getWorkAreaName());
        listInfo.setStationCode(info.getStationSimpleName());
        listInfo.setStationName(info.getStationName());
        listInfo.setAlarmType(po.getAlarmType());
        listInfo.setAlarmLevel(po.getAlarmLevel());
        listInfo.setDeviceName(po.getDeviceName());
        listInfo.setAlarmCount(po.getAlarmCount());
        return listInfo;
    }


    public static List<AlarmListInfo> adaptToAlarmLists(List<AlarmListPo> pos, Map<String, DeptHierarchyInfo> infoMap) {
        List<AlarmListInfo> result = new ArrayList<>();
        for (AlarmListPo po : pos) {
            AlarmListInfo listInfo = adaptToAlarmList(po, infoMap.get(po.getSourceTelecode()));
            result.add(listInfo);
        }
        return result;
    }

    public static List<AlarmInfo> adaptToAlarmInfos(List<AlarmInfoPo> pos, Map<String, DeptHierarchyInfo> infoMap) {
        List<AlarmInfo> infos = new ArrayList<>();
        for (AlarmInfoPo po : pos) {
            infos.add(adaptoAlarmInfo(po, infoMap.get(po.getSourceTeleCode())));
        }
        return infos;
    }

    public static List<RealTimeAlarmList> adaptToRealTimeAlarm(List<RealTimeAlarmListPo> pos, Map<String, String> stationNameMap) {
        if (CollectionUtils.isEmpty(pos)) {
            return Collections.EMPTY_LIST;
        }

        List<RealTimeAlarmList> result = new ArrayList<>();
        for (RealTimeAlarmListPo po : pos) {
            RealTimeAlarmList listitem = new RealTimeAlarmList();
            listitem.setSegmentCode(po.getSegmentCode());
            listitem.setWorkshopCode(po.getWorkshopCode());
            listitem.setWorkareaCode(po.getWorkareaCode());
            listitem.setStationCode(po.getStationCode());
            listitem.setStationName(stationNameMap.get(po.getStationCode()));
            listitem.setAlarmLevel(po.getAlarmLevel());
            listitem.setAlarmType(StandardAlarmType.codeOf(po.getAlarmType()));
            listitem.setAlarmContext(po.getAlarmContext());
            listitem.setDeviceName(po.getDeviceName());
            listitem.setAlarmCount(po.getAlarmCount());
            result.add(listitem);
        }
        return result;
    }

    public static List<AlarmInfoPo> adaptToAlarmPo(RespContext faultContext, DeptHierarchyInfo hinfo) {
        RespHeader header = faultContext.getHeader();
        RespBody body = faultContext.getBody();
        List<AlarmInfoPo> result = new ArrayList<>();
        List<RespRecord> respRecords = body.getRespRecords();
        for (RespRecord respRecord : respRecords) {
            AlarmInfoPo po = new AlarmInfoPo();
            po.setSegment(hinfo.getSegmentSimpleName());
            po.setWorkshopCode(hinfo.getWorkShopSimpleName());
            po.setWorkAreaCode(hinfo.getWorkAreaSimpleName());
            po.setTargetTeleCode(header.getTargetTeleCode());
            po.setSourceTeleCode(header.getSourceTeleCode());
            po.setAlarmLevel(respRecord.getLevel());
            po.setDeviceType(ByteBufferUtil.bytesToShort(respRecord.getDeviceType()));
            po.setDeviceNo(ByteBufferUtil.bytesToShort(respRecord.getDeviceNo()));
            po.setDeviceName(respRecord.getDeviceName() == null?"":respRecord.getDeviceName());
            po.setAlarmTime(respRecord.getHappenTime());
            po.setRecoverTime(respRecord.getRecoverTime());
            po.setStatus(AlarmStatus.ALARM);
            po.setAlarmContext(respRecord.getAlarmCtx());
            po.setOverhaulFlag(OverhaulType.OTHER);
            po.setCreateTime(new Date());
            po.setRemark("");
            po.setAlarmType(ByteBufferUtil.bytesToShort(ByteBufferUtil.subBytes(respRecord.getReserveField(), (byte) 2)));
            result.add(po);
        }
        return result;
    }
}
