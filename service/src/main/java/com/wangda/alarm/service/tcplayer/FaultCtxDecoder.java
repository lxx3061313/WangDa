package com.wangda.alarm.service.tcplayer;

import com.wangda.alarm.service.bean.FaultContext;
import com.wangda.alarm.service.bean.protocol.ProtocalFieldsDesc;
import com.wangda.alarm.service.util.ByteBufferUtil;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import javax.annotation.Resource;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-24
 */
@Service
public class FaultCtxDecoder implements MessageDecoder {

    @Resource
    FaultDataDecoder faultDataDecoder;

    private CharsetDecoder cd;
    public FaultCtxDecoder() {
        cd = Charset.forName("GBK").newDecoder();
    }

    @Override
    public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
        in.position(ProtocalFieldsDesc.FAULT_HEADER_DATACMD_CODE.getPosition());
        in.limit(ProtocalFieldsDesc.FAULT_HEADER_DATACMD_CODE.getLimit());
        byte dataCmd = in.get();
        MessageDecoderResult result;
        //1. 判断是命令还是数据
        if (dataCmd == 1) {
            in.flip();
            return MessageDecoderResult.NOT_OK;
        }

        //2. 判断压缩位
        in.position(ProtocalFieldsDesc.FAULT_HEADER_ZIP_FLAG.getPosition());
        in.limit(ProtocalFieldsDesc.FAULT_HEADER_ZIP_FLAG.getLimit());
        byte zipFlag = in.get();
        int iflag = ByteBufferUtil.byteToInt(zipFlag);
        //2.1 有压缩位
        if (iflag == 0 || iflag == 1) {
            in.position(ProtocalFieldsDesc.FAULT_HEADER_VERSION.getPosition());
            in.limit(ProtocalFieldsDesc.FAULT_HEADER_VERSION.getLimit());
            byte version = in.get();
            if (ByteBufferUtil.byteToInt(version) == 0xe0) {
                result = MessageDecoderResult.OK;
            } else {
                result = MessageDecoderResult.NOT_OK;
            }
        } else {
            result = MessageDecoderResult.NOT_OK;
        }

        in.flip();
        return result;
    }

    @Override
    public MessageDecoderResult decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
            throws Exception {
        IoBuffer buffer = IoBuffer.allocate(100).setAutoExpand(true);
        while (in.hasRemaining()) {
            byte b = in.get();
            buffer.put(b);
        }
        buffer.flip();
        FaultContext faultContext = faultDataDecoder.decodeData(buffer, cd);
        out.write(faultContext);
        return MessageDecoderResult.OK;
    }

    @Override
    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {

    }
}
