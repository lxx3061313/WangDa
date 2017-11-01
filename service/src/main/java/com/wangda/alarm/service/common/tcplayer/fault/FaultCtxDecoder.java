package com.wangda.alarm.service.common.tcplayer.fault;

import com.wangda.alarm.service.bean.standard.DataType;
import com.wangda.alarm.service.bean.standard.DataTypeCode;
import com.wangda.alarm.service.bean.standard.alarminfo.fault.FaultContext;
import com.wangda.alarm.service.bean.standard.protocol.ProtocalFieldsDesc;
import com.wangda.alarm.service.common.tcplayer.common.WangDaContextDecoder;
import com.wangda.alarm.service.common.util.ByteBufferUtil;
import java.util.function.Consumer;
import javax.annotation.Resource;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-24
 */
@Service
public class FaultCtxDecoder extends WangDaContextDecoder<FaultContext> {

    private static final byte FAULT_ZIP_FLAG = (byte)0x00;
    @Resource
    FaultDataDecoder faultDataDecoder;

    public FaultCtxDecoder() {
    }

    @Override
    public MessageDecoderResult decodeData(IoSession session, IoBuffer in,
            Consumer<FaultContext> callback) {
        FaultContext faultContext = faultDataDecoder.decodeData(in, cd);
        callback.accept(faultContext);
        return MessageDecoderResult.OK;
    }

    @Override
    public MessageDecoderResult intervalDecodeable(IoSession session, IoBuffer in) {
        MessageDecoderResult result;
        //1. 判断是命令还是数据
        byte dataCmd = ByteBufferUtil.extractByte(in, ProtocalFieldsDesc.FAULT_HEADER_DATACMD_CODE);
        if (dataCmd == DataType.CMD.getCode()) {
            in.flip();
            return MessageDecoderResult.NOT_OK;
        }

        //2. 判断压缩位
        byte zipFlag = ByteBufferUtil.extractByte(in, ProtocalFieldsDesc.FAULT_HEADER_ZIP_FLAG);
        if (zipFlag != FAULT_ZIP_FLAG) {
            in.flip();
            return MessageDecoderResult.NOT_OK;
        }


        //3. 判断是数据类型
        byte dataTypeCode = ByteBufferUtil.extractByte(in, ProtocalFieldsDesc.FAULT_HEADER_DATA_TYPE);
        byte dataSubTypeCode = ByteBufferUtil.extractByte(in, ProtocalFieldsDesc.FAULT_HEADER_DATA_SUBTYPE);

        if (DataTypeCode.FAULT_NOC.getDataType() == dataTypeCode &&
                DataTypeCode.FAULT_NOC.getDataSubType() == dataSubTypeCode) {
            result = MessageDecoderResult.OK;
        } else {
            result = MessageDecoderResult.NOT_OK;
        }
        in.flip();
        return result;
    }
}
