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

        try {
            ByteBufferUtil.storeCtx(buffer);
            buffer.position(ProtocalFieldsDesc.ALARM_BODY_ZIPFLAG.getPosition());
            //压缩标志
            AlarmBody body = new AlarmBody();
            body.setZip(buffer.get());
            //数据命令
            body.setDataCmd(buffer.get());
            //数据子命令
            body.setDataSubCmd(buffer.get());
            //报警级别
            body.setAlartLevel(AlarmLevel.codeOf(buffer.get()));
            //报警类型
            byte[] alarmType = ByteBufferUtil
                    .forward(buffer, ProtocalFieldsDesc.ALARM_BODY_ALARM_TYPE.getByteLth());
            body.setAlartType(alarmType);
            //设备类型
            byte[] deviceType = ByteBufferUtil
                    .forward(buffer, ProtocalFieldsDesc.ALARM_BODY_DEVICE_TYPE.getByteLth());
            body.setDeviceType(deviceType);
            //设备序号
            byte[] deviveNo = ByteBufferUtil.forward(buffer, ProtocalFieldsDesc.ALARM_BODY_DEVICE_NO.getByteLth());
            body.setDeviceNo(deviveNo);
            //报警时间
            byte[] alarmTime = ByteBufferUtil
                    .forward(buffer, ProtocalFieldsDesc.ALARM_BODY_ALARM_HTIME.getByteLth());
            body.setAlarmTime(ByteBufferUtil.byteToDate(alarmTime));
            //恢复时间
            byte[] rtime = ByteBufferUtil
                    .forward(buffer, ProtocalFieldsDesc.ALARM_BODY_ALARM_RTIME.getByteLth());
            body.setRecoverTime(ByteBufferUtil.byteToDate(rtime));
            //报警order
            byte[] order = ByteBufferUtil
                    .forward(buffer, ProtocalFieldsDesc.ALARM_BODY_ALARM_ORDER.getByteLth());
            body.setAlarmOrder(order);
            //报警状态
            byte status = buffer.get();
            if (status == 0) {
                body.setStatus(AlarmStatus.RECOVER);
            } else if (status == 1) {
                body.setStatus(AlarmStatus.ALARM);
            }
            //4.11 报警描述长度
            byte ctxLth = buffer.get();
            body.setAlarmCtxLenght(ctxLth);
            //4.12 设备名称长度
            byte dnamelth = buffer.get();
            body.setDeviceNameLength(dnamelth);
            //4.13 报警描述(动态长度)
            byte[] ctx = ByteBufferUtil.forward(buffer, ctxLth);
            body.setAlarmCtx(ByteBufferUtil.bytesToString(ctx, cd));
            //4.14 设备长度
            byte[] dname = ByteBufferUtil.forward(buffer, dnamelth);
            body.setDeviceName(ByteBufferUtil.bytesToString(dname, cd));
            //4.15 检修标志
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
        } finally {
            ByteBufferUtil.recoverCtx(buffer);
        }

    }
}
