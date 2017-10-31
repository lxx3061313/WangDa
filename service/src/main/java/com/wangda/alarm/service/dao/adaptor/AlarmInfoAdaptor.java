package com.wangda.alarm.service.dao.adaptor;

import com.wangda.alarm.service.bean.biz.AlarmInfo;
import com.wangda.alarm.service.bean.biz.DeptHierarchyInfo;
import com.wangda.alarm.service.bean.standard.OverhaulType;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmBody;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmContext;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmHeader;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmStatus;
import com.wangda.alarm.service.bean.standard.alarminfo.fault.FaultBody;
import com.wangda.alarm.service.bean.standard.alarminfo.fault.FaultContext;
import com.wangda.alarm.service.bean.standard.alarminfo.fault.FaultHeader;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.RespBody;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.RespContext;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.RespHeader;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.RespRecord;
import com.wangda.alarm.service.common.util.ByteBufferUtil;
import com.wangda.alarm.service.dao.po.AlarmExtInfoPo;
import com.wangda.alarm.service.dao.po.AlarmInfoPo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
        alarmInfo.setStatus(po.getStatus());
        return alarmInfo;
    }

    public static List<AlarmInfo> adaptToAlarmInfos(List<AlarmInfoPo> pos) {
        if (CollectionUtils.isEmpty(pos)) {
            return Collections.EMPTY_LIST;
        }
        return pos.stream().map(AlarmInfoAdaptor::adaptoAlarmInfo).collect(Collectors.toList());
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
            po.setAlarmType(0);
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
            result.add(po);
        }
        return result;
    }
}
