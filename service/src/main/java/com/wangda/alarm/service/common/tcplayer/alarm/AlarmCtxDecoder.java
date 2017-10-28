package com.wangda.alarm.service.common.tcplayer.alarm;

import com.wangda.alarm.service.bean.standard.DataType;
import com.wangda.alarm.service.bean.standard.DataTypeCode;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmContext;
import com.wangda.alarm.service.bean.standard.protocol.ProtocalFieldsDesc;
import com.wangda.alarm.service.common.tcplayer.common.WangDaContextDecoder;
import com.wangda.alarm.service.common.util.ByteBufferUtil;
import javax.annotation.Resource;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
@Service
public class AlarmCtxDecoder extends WangDaContextDecoder<AlarmContext> {
    private final static byte ALARM_VERSION_CODE = (byte)0xE0;
    @Resource
    AlarmDataDecoder alarmDataDecoder;

    public AlarmCtxDecoder() {
    }

    @Override
    public AlarmContext decodeData(IoSession session, IoBuffer in) {
        return alarmDataDecoder.decodeData(in, cd);
    }

    @Override
    public MessageDecoderResult intervalDecodeable(IoSession session, IoBuffer in) {
        MessageDecoderResult result;

        //1. 判断是命令还是数据
        byte dataCmd = ByteBufferUtil.extractByte(in, ProtocalFieldsDesc.ALARM_HEADER_DATACMD_CODE);
        if (dataCmd == DataType.CMD.getCode()) {
            in.flip();
            return MessageDecoderResult.NOT_OK;
        }

        //2. 判断是否有压缩位(报警没有压缩标记)
        byte version = ByteBufferUtil.extractByte(in, ProtocalFieldsDesc.ALARM_HEADER_VERSION);
        if (version != ALARM_VERSION_CODE) {
            in.flip();
            return MessageDecoderResult.NOT_OK;
        }

        //3. 数据类型码
        byte dataCode = ByteBufferUtil.extractByte(in, ProtocalFieldsDesc.ALARM_HEADER_DATA_TYPE_CODE);
        byte subDataCode = ByteBufferUtil.extractByte(in, ProtocalFieldsDesc.ALARM_HEADER_DATA_SUBTYPE_CODE);
        if (DataTypeCode.ALARM_DATA.getDataType() == dataCode &&
                DataTypeCode.ALARM_DATA.getDataSubType() == subDataCode) {
            result = MessageDecoderResult.OK;
        } else {
            result = MessageDecoderResult.NOT_OK;
        }
        return result;
    }
}
