package com.wangda.alarm.service.common.tcplayer.resp;

import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmLevel;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.RespBody;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.RespRecord;
import com.wangda.alarm.service.bean.standard.protocol.ProtocalFieldsDesc;
import com.wangda.alarm.service.common.util.ByteBufferUtil;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-24
 */
@Service
public class RespBodyDecoder {
    private final static Logger logger  = LoggerFactory.getLogger(RespBodyDecoder.class);

    public RespBody bodyDecode(IoBuffer buffer, CharsetDecoder cd) {
        ByteBufferUtil.storeCtx(buffer);
        try {
            // 0. 其实字节位置
            buffer.position(ProtocalFieldsDesc.RESP_BODY_DATA_NAME.getPosition());

            RespBody body = new RespBody();
            //1. 数据命名-1字节
            body.setDataName(buffer.get());

            //2. 记录数
            byte[] recordNum = ByteBufferUtil
                    .forward(buffer, ProtocalFieldsDesc.RESP_BODY_RECORD_NUM.getByteLth());
            short irecordNum = ByteBufferUtil.byteToShort(recordNum);
            body.setRecordNum(ByteBufferUtil.byteToShort(recordNum));

            //3. 记录
            List<RespRecord> respRecords = new ArrayList<>();
            for (int i=0; i < irecordNum; ++i) {
                RespRecord record = new RespRecord();
                //3.1 发生时间
                byte[] htime = ByteBufferUtil
                        .forward(buffer, ProtocalFieldsDesc.RESP_BODY_HAPPEN_TIME.getByteLth());
                record.setHappenTime(ByteBufferUtil.byteToDate(htime));

                //3.2 恢复时间
                byte[] rtime = ByteBufferUtil.forward(buffer, ProtocalFieldsDesc.RESP_BODY_RECOVER_TIME.getByteLth());
                record.setRecoverTime(ByteBufferUtil.byteToDate(rtime));

                //3.3 预留字段
                byte[] refield = ByteBufferUtil.forward(buffer, ProtocalFieldsDesc.RESP_BODY_RESERVE_FIELD
                        .getByteLth());
                record.setReserveField(refield);

                //3.4 设备名长度
                byte[] dviLth = ByteBufferUtil
                        .forward(buffer, ProtocalFieldsDesc.RESP_BODY_DEVICE_NAMELTH.getByteLth());
                record.setDeviceNameLth(dviLth[0]);

                //3.5 设备名
                if (dviLth[0] != 0) {
                    try {
                        byte[] dname = ByteBufferUtil.forward(buffer, dviLth[0]);
                        record.setDeviceName(ByteBufferUtil.bytesToString(dname, cd));
                    } catch (CharacterCodingException e) {
                        record.setDeviceName("unknow");
                    }
                }

                //3.6 报警内容长度
                byte[] alarmCtxLth = ByteBufferUtil
                        .forward(buffer, ProtocalFieldsDesc.RESP_BODY_ALARM_CTXLTH.getByteLth());
                record.setAlarmCtxLth(alarmCtxLth[0]);

                //3.7 报警内容
                if (alarmCtxLth[0] != 0) {
                    try {
                        byte[] actx = ByteBufferUtil.forward(buffer, alarmCtxLth[0]);
                        record.setAlarmCtx(ByteBufferUtil.bytesToString(actx, cd));
                    } catch (CharacterCodingException e) {
                        record.setAlarmCtx("unknow");
                    }
                }

                //3.8 预留长度
                byte[] r = ByteBufferUtil
                        .forward(buffer, ProtocalFieldsDesc.RESP_BODY_RESERVE1.getByteLth());
                record.setReserve1(r);

                //3.11 设备类型
                byte[] dtype = ByteBufferUtil
                        .forward(buffer, ProtocalFieldsDesc.RESP_BODY_DEVICE_TYPE.getByteLth());
                record.setDeviceType(dtype);

                //3.12 设备序号
                byte[] dno = ByteBufferUtil
                        .forward(buffer, ProtocalFieldsDesc.RESP_BODY_DEVICE_NO.getByteLth());
                record.setDeviceNo(dno);
                respRecords.add(record);

                //3.10 报警级别
                byte[] level = ByteBufferUtil
                        .forward(buffer, ProtocalFieldsDesc.RESP_BODY_ALARM_LEVEL.getByteLth());
                record.setLevel(AlarmLevel.codeOf(level[0]));

                //3.11 报警车站编码
                byte[]  stateCode = ByteBufferUtil.forward(buffer, ProtocalFieldsDesc.RESP_BODY_ALARM_STATION_CODE.getByteLth());
                try {
                    String code = ByteBufferUtil.bytesToString(stateCode, cd);
                    record.setStationCode(code);
                } catch (CharacterCodingException e) {
                    logger.error("车站编码解析异常");
                    record.setStationCode("unknown");
                }

            }
            body.setRespRecords(respRecords);
            return body;
        } finally {
            ByteBufferUtil.recoverCtx(buffer);
        }
    }
}
