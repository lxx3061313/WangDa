package com.wangda.alarm.service.tcplayer;

import com.wangda.alarm.service.bean.AlarmBean;
import com.wangda.alarm.service.bean.AlarmContext;
import com.wangda.alarm.service.bean.AlarmHeader;
import com.wangda.alarm.service.bean.BizBeanType;
import com.wangda.alarm.service.bean.DataType;
import com.wangda.alarm.service.bean.protocol.ProtocalFieldsDesc;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
public class AlarmCtxDecoder extends CumulativeProtocolDecoder {
    private final Charset charset;
    CharsetDecoder cd;
    public AlarmCtxDecoder(Charset charset) {
        this.charset = charset;
        cd = charset.newDecoder();
    }

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
            throws Exception {
        IoBuffer buffer = IoBuffer.allocate(200).setAutoExpand(true);
        while (in.hasRemaining()) {
            byte b = in.get();
            buffer.put(b);
        }
        buffer.flip();

        AlarmBean context = new AlarmBean();
        //1. data_len
        buffer.position(ProtocalFieldsDesc.PROTOCOL_DATA_LENGTH.getPosition());
        buffer.limit(ProtocalFieldsDesc.PROTOCOL_DATA_LENGTH.getLimit());
        context.setDataLength(buffer.getInt());
        buffer.flip();

        //2. flag of data or heart
        buffer.position(ProtocalFieldsDesc.PROTOCOL_DATA_TYPE.getPosition());
        buffer.limit(ProtocalFieldsDesc.PROTOCOL_DATA_TYPE.getLimit());
        byte flag = buffer.get();
        if (flag == 0x8f) {
            context.setBeanType(BizBeanType.DATA);
        } else if (flag == 0x0f) {
            context.setBeanType(BizBeanType.HEART);
        }

        //3. data context
        AlarmHeader header = new AlarmHeader();
        //3.1 target tele code
        buffer.position(ProtocalFieldsDesc.ALARM_HEADER_TARGET_TELECODE.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_HEADER_TARGET_TELECODE.getLimit());
        String target = buffer.getString(cd);
        header.setTargetTeleCode(target);
        //3.2 source tele code
        buffer.position(ProtocalFieldsDesc.ALARM_HEADER_SOURCE_TELECODE.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_HEADER_SOURCE_TELECODE.getLimit());
        String source = buffer.getString(cd);
        header.setTargetTeleCode(source);
        //3.3 data cmd code
        buffer.position(ProtocalFieldsDesc.ALARM_HEADER_DATACMD_CODE.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_HEADER_DATACMD_CODE.getLimit());
        byte datacmd = buffer.get();
        if (datacmd == 0x00) {
            header.setDataType(DataType.DATA);
        } else if (datacmd == 0x01) {
            header.setDataType(DataType.CMD);
        }
        //3.4 报警没有压缩标记
        //3.5 版本码
        buffer.position(ProtocalFieldsDesc.ALARM_HEADER_VERSION.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_HEADER_VERSION.getLimit());
        header.setVersion(buffer.get());
        //3.4 数据类型码
        buffer.position(ProtocalFieldsDesc.ALARM_HEADER_DATA_TYPE_CODE.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_HEADER_DATA_TYPE_CODE.getLimit());
        header.setDataTypeCode(buffer.get());
        //3.5 数据子类型码
        buffer.position(ProtocalFieldsDesc.ALARM_HEADER_DATA_SUBTYPE_CODE.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_HEADER_DATA_SUBTYPE_CODE.getLimit());
        header.setSubDataTypeCode(buffer.get());
        return false;
    }
}
