package com.wangda.alarm.service.common.tcplayer.resp;

import com.sun.org.apache.regexp.internal.RE;
import com.wangda.alarm.service.bean.standard.DataType;
import com.wangda.alarm.service.bean.standard.DataTypeCode;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.RespContext;
import com.wangda.alarm.service.bean.standard.protocol.ProtocalFieldsDesc;
import com.wangda.alarm.service.common.tcplayer.common.WangDaContextDecoder;
import com.wangda.alarm.service.common.util.ByteBufferUtil;
import java.util.function.Consumer;
import javax.annotation.Resource;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import org.springframework.stereotype.Service;

/**
 * @author wenlai
 * @version 2017-10-25
 */
@Service
public class RespContextDecoder extends WangDaContextDecoder<RespContext> {


    private final AttributeKey CONTEXT = new AttributeKey(getClass(), "conext");

    private static byte RESP_ZIP_FLAG = (byte)0;
    @Resource
    RespDataDecoder respDataDecoder;

    public RespContextDecoder() {
    }

    @Override
    public MessageDecoderResult decodeData(IoSession session, IoBuffer in,
            Consumer<RespContext> callback) {
        RespContext respContext = respDataDecoder.decodeData(in, cd);
        callback.accept(respContext);
        return MessageDecoderResult.OK;
    }

    @Override
    public MessageDecoderResult intervalDecodeable(IoSession session, IoBuffer in) {
        MessageDecoderResult result;
        byte dataCmd = ByteBufferUtil.extractByte(in, ProtocalFieldsDesc.ALARM_HEADER_DATACMD_CODE);
        //1. 判断是命令还是数据
        if (dataCmd == DataType.CMD.getCode()) {
            in.flip();
            return MessageDecoderResult.NOT_OK;
        }

        //2. 判断是否有压缩位
        byte zipFlag = ByteBufferUtil.extractByte(in, ProtocalFieldsDesc.RESP_HEADER_ZIP_FLAG);
        //2.1 没有压缩位
        if (zipFlag != RESP_ZIP_FLAG) {
            in.flip();
            return MessageDecoderResult.NOT_OK;
        }

        byte dataTypeCode = ByteBufferUtil.extractByte(in, ProtocalFieldsDesc.RESP_HEADER_DATA_TYPE);
        byte dataSubTypeCode = ByteBufferUtil.extractByte(in, ProtocalFieldsDesc.RESP_HEADER_DATA_SUBTYPE);
        if (DataTypeCode.QUERY_RESP.getDataType() == dataTypeCode &&
                DataTypeCode.QUERY_RESP.getDataSubType() == dataSubTypeCode) {
            result = MessageDecoderResult.OK;
        } else {
            result = MessageDecoderResult.NOT_OK;
        }
        return result;
    }
}
