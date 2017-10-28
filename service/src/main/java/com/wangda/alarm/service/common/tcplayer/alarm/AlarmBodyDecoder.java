package com.wangda.alarm.service.common.tcplayer.alarm;

import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmBody;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmLevel;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmStatus;
import com.wangda.alarm.service.bean.standard.OverhaulType;
import com.wangda.alarm.service.bean.standard.protocol.ProtocalFieldsDesc;
import com.wangda.alarm.service.common.util.ByteBufferUtil;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.util.Date;
import org.apache.mina.core.buffer.IoBuffer;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-24
 */
@Service
public class AlarmBodyDecoder {
    public AlarmBody bodyDecode(IoBuffer buffer, CharsetDecoder cd)
            throws CharacterCodingException {
        //4. body
        //4.1 压缩标志
        AlarmBody body = new AlarmBody();
        buffer.position(ProtocalFieldsDesc.ALARM_BODY_ZIPFLAG.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BODY_ZIPFLAG.getLimit());
        body.setZip(buffer.get());
        //4.2 数据命令
        buffer.position(ProtocalFieldsDesc.ALARM_BODY_DATACMD.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BODY_DATACMD.getLimit());
        body.setDataCmd(buffer.get());
        //4.3 数据子命令
        buffer.position(ProtocalFieldsDesc.ALARM_BODY_DATASUMCMD.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BODY_DATASUMCMD.getLimit());
        body.setDataSubCmd(buffer.get());
        //4.4 报警级别
        buffer.position(ProtocalFieldsDesc.ALARM_BDDY_ALARM_LEVEL.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BDDY_ALARM_LEVEL.getLimit());
        body.setAlartLevel(AlarmLevel.codeOf(buffer.get()));
        //4.5 报警类型
        buffer.position(ProtocalFieldsDesc.ALARM_BODY_ALARM_TYPE.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BODY_ALARM_TYPE.getLimit());
        byte[] ctype = new byte[2];
        buffer.get(ctype);
        body.setAlartType(ctype);
        //4.6 设备类型
        buffer.position(ProtocalFieldsDesc.ALARM_BODY_DEVICE_TYPE.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BODY_DEVICE_TYPE.getLimit());
        byte[] dtype = new byte[2];
        buffer.get(dtype);
        body.setDeviceType(dtype);
        //4.7 设备序号
        buffer.position(ProtocalFieldsDesc.ALARM_BODY_DEVICE_NO.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BODY_DEVICE_NO.getLimit());
        byte[] dno = new byte[2];
        buffer.get(dno);
        body.setDeviceNo(dno);
        //4.7 报警时间
        buffer.position(ProtocalFieldsDesc.ALARM_BODY_ALARM_HTIME.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BODY_ALARM_HTIME.getLimit());
        byte [] atime = new byte[4];
        buffer.get(atime);
        int atimestamp = ByteBufferUtil.bytesToInt(atime, 0);
        body.setAlarmTime(new Date(atimestamp * 1000L));
        //4.8 恢复时间
        buffer.position(ProtocalFieldsDesc.ALARM_BODY_ALARM_RTIME.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BODY_ALARM_RTIME.getLimit());
        byte [] rtime = new byte[4];
        buffer.get(rtime);
        int rtimestamp = ByteBufferUtil.bytesToInt(rtime, 0);
        body.setRecoverTime(rtimestamp == 0? null : new Date(rtimestamp * 1000L));
        //4.9 报警order
        buffer.position(ProtocalFieldsDesc.ALARM_BODY_ALARM_ORDER.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BODY_ALARM_ORDER.getLimit());
        byte [] order = new byte[2];
        buffer.get(order);
        body.setAlarmOrder(order);
        //4.10 报警状态
        buffer.position(ProtocalFieldsDesc.ALARM_BODY_ALARM_STATUS.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BODY_ALARM_STATUS.getLimit());
        byte status = buffer.get();
        if (status == 0) {
            body.setStatus(AlarmStatus.RECOVER);
        } else if (status == 1) {
            body.setStatus(AlarmStatus.ALARM);
        }
        //4.11 报警描述长度
        buffer.position(ProtocalFieldsDesc.ALARM_BODY_CONTEXT_LENGTH.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BODY_CONTEXT_LENGTH.getLimit());
        byte ctxLth = buffer.get();
        body.setAlarmCtxLenght(ctxLth);
        //4.12 设备名称长度
        buffer.position(ProtocalFieldsDesc.ALARM_BODY_DEVICENAME_LENGTH.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BODY_DEVICENAME_LENGTH.getLimit());
        byte dnamelth = buffer.get();
        body.setDeviceNameLength(dnamelth);
        //4.13 报警描述(动态长度)
        int currentLimit = buffer.limit();
        buffer.position(currentLimit);
        buffer.limit(currentLimit + ctxLth);
        body.setAlarmCtx(buffer.getString(cd));
        //4.14 设备长度
        currentLimit = buffer.limit();
        buffer.position(currentLimit);
        buffer.limit(currentLimit + dnamelth);
        body.setDeviceName(buffer.getString(cd));
        //4.15 检修标志
        currentLimit = buffer.limit();
        buffer.position(currentLimit);
        buffer.limit(currentLimit+1);
        byte haulflag = buffer.get();
        if (haulflag == 1) {
            body.setOverhaulType(OverhaulType.OVERHAUL);
        } else if (haulflag == 2) {
            body.setOverhaulType(OverhaulType.SKYLIGHT);
        } else {
            body.setOverhaulType(OverhaulType.OTHER);
        }
        //4.16 预留长度
//        currentLimit = buffer.limit();
//        buffer.position(currentLimit);
//        buffer.limit(currentLimit + 1);
//        byte reserveLth = buffer.get();
//        body.setReservedCtxLth(reserveLth);
//        //4.17 预留内容
//        if (reserveLth != 0) {
//            currentLimit = buffer.limit();
//            buffer.position(currentLimit);
//            buffer.limit(currentLimit+reserveLth);
//            body.setReservedCtx(buffer.getString(cd));
//        }
        return body;
    }
}
