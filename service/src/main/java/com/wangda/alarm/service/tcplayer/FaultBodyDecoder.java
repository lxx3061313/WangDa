package com.wangda.alarm.service.tcplayer;

import com.wangda.alarm.service.bean.AlarmBody;
import com.wangda.alarm.service.bean.FaultBody;
import com.wangda.alarm.service.bean.FaultRecord;
import com.wangda.alarm.service.bean.protocol.ProtocalFieldsDesc;
import com.wangda.alarm.service.util.ByteBufferUtil;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Date;
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
        FaultBody body = new FaultBody();
        //1. 实时数据
        buffer.position(ProtocalFieldsDesc.FAULT_BODY_REALTIME_FLAG.getPosition());
        buffer.limit(ProtocalFieldsDesc.FAULT_BODY_REALTIME_FLAG.getLimit());
        body.setRealTimeData(buffer.get());
        //2. 记录数
        buffer.position(ProtocalFieldsDesc.FAULT_BODY_RECORD_NUM.getPosition());
        buffer.limit(ProtocalFieldsDesc.FAULT_BODY_RECORD_NUM.getLimit());
        byte [] baRecordNum = new byte[2];
        buffer.get(baRecordNum);
        body.setRecordNum(ByteBufferUtil.bytesToShort(baRecordNum, 0));
        //3. 记录
        //3.1 通知时间
        FaultRecord record = new FaultRecord();
        buffer.position(ProtocalFieldsDesc.FAULT_BODY_NOC_TIME.getPosition());
        buffer.limit(ProtocalFieldsDesc.FAULT_BODY_NOC_TIME.getLimit());
        record.setNocTime(ByteBufferUtil.byteToDate(buffer));
        //3.2 受理时间
        buffer.position(ProtocalFieldsDesc.FAULT_BODY_PROC_TIME.getPosition());
        buffer.limit(ProtocalFieldsDesc.FAULT_BODY_PROC_TIME.getLimit());
        record.setProcessTime(ByteBufferUtil.byteToDate(buffer));
        //3.3 恢复时间
        buffer.position(ProtocalFieldsDesc.FAULT_BODY_RECOVER_TIME.getPosition());
        buffer.limit(ProtocalFieldsDesc.FAULT_BODY_RECOVER_TIME.getLimit());
        record.setRecoverTime(ByteBufferUtil.byteToDate(buffer));
        //3.4 预留时间
        buffer.position(ProtocalFieldsDesc.FAULT_BODY_RESERVE_TIME.getPosition());
        buffer.limit(ProtocalFieldsDesc.FAULT_BODY_RESERVE_TIME.getLimit());
        record.setReserveTime(ByteBufferUtil.byteToDate(buffer));
        //3.5 故障原因长度
        buffer.position(ProtocalFieldsDesc.FAULT_BODY_REASON_LENGTH.getPosition());
        buffer.limit(ProtocalFieldsDesc.FAULT_BODY_REASON_LENGTH.getLimit());
        record.setFaultReasonLth(buffer.get());
        //3.6 故障原因
        if (record.getFaultReasonLth() > 0) {
            int limit = buffer.limit();
            buffer.position(limit);
            buffer.limit(limit + record.getFaultReasonLth());
            String reason = buffer.getString(cd);
            record.setFaultReason(reason);
        }
        List<FaultRecord> records = new ArrayList<>();
        records.add(record);
        body.setRecords(records);
        return body;
    }
}
