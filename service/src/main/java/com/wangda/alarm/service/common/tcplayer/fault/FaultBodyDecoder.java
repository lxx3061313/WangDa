package com.wangda.alarm.service.common.tcplayer.fault;

import com.wangda.alarm.service.bean.standard.alarminfo.fault.FaultBody;
import com.wangda.alarm.service.bean.standard.alarminfo.fault.FaultRecord;
import com.wangda.alarm.service.bean.standard.protocol.ProtocalFieldsDesc;
import com.wangda.alarm.service.common.util.ByteBufferUtil;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.mina.core.buffer.IoBuffer;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-24
 */
@Service
public class FaultBodyDecoder {
    public FaultBody bodyDecode(IoBuffer buffer, CharsetDecoder cd)
            throws CharacterCodingException {

        buffer.position(ProtocalFieldsDesc.FAULT_BODY_REALTIME_FLAG.getPosition());

        FaultBody body = new FaultBody();
        //1. 实时数据
        body.setRealTimeData(buffer.get());

        //2. 记录数
        byte[] rnum = ByteBufferUtil
                .forward(buffer, ProtocalFieldsDesc.FAULT_BODY_RECORD_NUM.getByteLth());
        short srnum = ByteBufferUtil.bytesToShort(rnum);
        body.setRecordNum(ByteBufferUtil.bytesToShort(rnum));

        List<FaultRecord> records = new ArrayList<>();
        for (int i=0;i < srnum; ++i) {
            FaultRecord record = new FaultRecord();
            //3.1 通知时间
            byte[] noctime = ByteBufferUtil
                    .forward(buffer, ProtocalFieldsDesc.FAULT_BODY_NOC_TIME.getByteLth());
            record.setNocTime(ByteBufferUtil.byteToDate(noctime));

            //3.2 受理时间
            byte[] proTime = ByteBufferUtil
                    .forward(buffer, ProtocalFieldsDesc.FAULT_BODY_PROC_TIME.getByteLth());
            record.setProcessTime(ByteBufferUtil.byteToDate(proTime));

            //3.3 恢复时间
            byte[] rtime = ByteBufferUtil
                    .forward(buffer, ProtocalFieldsDesc.FAULT_BODY_RECOVER_TIME.getByteLth());
            record.setRecoverTime(ByteBufferUtil.byteToDate(rtime));

            //3.4 预留时间
            byte[] retime = ByteBufferUtil
                    .forward(buffer, ProtocalFieldsDesc.FAULT_BODY_RESERVE_TIME.getByteLth());
            record.setReserveTime(ByteBufferUtil.byteToDate(retime));

            //3.5 故障原因长度
            byte seasonLth = buffer.get();
            record.setFaultReasonLth(seasonLth);

            //3.6 故障原因
            if (seasonLth > 0) {
                byte[] ctx = ByteBufferUtil.forward(buffer, seasonLth);
                record.setFaultReason(ByteBufferUtil.bytesToString(ctx, cd));
            }
            records.add(record);
        }
        body.setRecords(records);
        return body;
    }
}
