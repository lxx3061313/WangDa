package com.wangda.alarm.service.tcplayer.resp;

import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmLevel;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.RespBody;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.RespRecord;
import com.wangda.alarm.service.bean.standard.protocol.ProtocalFieldsDesc;
import com.wangda.alarm.service.util.ByteBufferUtil;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.mina.core.buffer.IoBuffer;

/**
 * @author lixiaoxiong
 * @version 2017-10-24
 */
public class RespBodyDecoder {
    public RespBody bodyDecode(IoBuffer buffer, CharsetDecoder cd) {
        RespBody body = new RespBody();
        //1. 数据命名
        body.setDataName(ByteBufferUtil.extractByte(buffer, ProtocalFieldsDesc.RESP_BODY_DATA_NAME));

        //2. 记录数
        short recordNum = ByteBufferUtil.extractShort(buffer, ProtocalFieldsDesc.RESP_BODY_RECORD_NUM);
        body.setRecordNum(recordNum);

        //3. 记录
        List<RespRecord> respRecords = new ArrayList<>();
        int position = buffer.limit();
        for (int i=0; i < recordNum; ++i) {
            RespRecord record = new RespRecord();
            //3.1 发生时间
            position = buffer.limit();
            record.setHappenTime(ByteBufferUtil.extractDate(buffer, position,
                    position + ProtocalFieldsDesc.RESP_BODY_HAPPEN_TIME.getByteLth()));

            //3.2 恢复时间
            position = buffer.limit();
            record.setRecoverTime(ByteBufferUtil.extractDate(buffer, position,
                    position + ProtocalFieldsDesc.RESP_BODY_RECOVER_TIME.getByteLth()));

            //3.3 预留时间
            position = buffer.limit();
            record.setReserveTime(ByteBufferUtil.extractDate(buffer, position,
                    position + ProtocalFieldsDesc.RESP_BODY_RESERVE_TIME.getByteLth()));

            //3.4 设备名长度
            position = buffer.limit();
            byte dnameLth = ByteBufferUtil.extractByte(buffer, position,
                    position + ProtocalFieldsDesc.RESP_BODY_DEVICE_NAMELTH.getByteLth());
            record.setDeviceNameLth(dnameLth);

            //3.5 设备名
            position = buffer.limit();
            try {
                record.setDeviceName(ByteBufferUtil.extractString(buffer, position, position + dnameLth, cd));
            } catch (CharacterCodingException e) {
                record.setDeviceName("unknow");
            }

            //3.6 报警内容长度
            position = buffer.limit();
            byte alarmctxLth = ByteBufferUtil.extractByte(buffer, position,
                    position + ProtocalFieldsDesc.RESP_BODY_ALARM_CTXLTH.getByteLth());
            record.setAlarmCtxLth(alarmctxLth);

            //3.7 报警内容
            position = buffer.limit();
            try {
                record.setAlarmCtx(ByteBufferUtil.extractString(buffer, position, position + alarmctxLth, cd));
            } catch (CharacterCodingException e) {
                record.setAlarmCtx("unknow");
            }

            //3.8 预留1
            position = buffer.limit();
            record.setReserve1(ByteBufferUtil.extractByteArray(buffer, position,
                    position + ProtocalFieldsDesc.RESP_BODY_RESERVE1.getByteLth(), 2));

            //3.9 预留2
            position = buffer.limit();
            record.setReserve2(ByteBufferUtil.extractByteArray(buffer, position,
                    position + ProtocalFieldsDesc.RESP_BODY_RESERVE2.getByteLth(), 2));

            //3.10 报警级别
            position = buffer.limit();
            byte level = ByteBufferUtil.extractByte(buffer, position,
                    position + ProtocalFieldsDesc.RESP_BODY_ALARM_LEVEL.getByteLth());
            record.setLevel(AlarmLevel.codeOf(level));

            //3.11 设备类型
            position = buffer.limit();
            record.setDeviceType(ByteBufferUtil.extractByteArray(buffer, position,
                    position + ProtocalFieldsDesc.RESP_BODY_DEVICE_TYPE.getByteLth(), 2));

            //3.12 设备序号
            position = buffer.limit();
            record.setDeviceNo(ByteBufferUtil.extractByteArray(buffer, position,
                    position + ProtocalFieldsDesc.RESP_BODY_DEVICE_NO.getByteLth(), 2));
            respRecords.add(record);
        }
        body.setRespRecords(respRecords);
        return body;
    }
}
