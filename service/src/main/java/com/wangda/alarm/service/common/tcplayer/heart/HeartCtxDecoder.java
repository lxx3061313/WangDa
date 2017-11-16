package com.wangda.alarm.service.common.tcplayer.heart;

import com.sun.org.apache.regexp.internal.RE;
import com.wangda.alarm.service.bean.standard.alarminfo.heart.HeartContext;
import com.wangda.alarm.service.bean.standard.alarminfo.heart.HeartMsg;
import com.wangda.alarm.service.bean.standard.constant.KeepAliveMsg;
import com.wangda.alarm.service.bean.standard.protocol.ProtocalFieldsDesc;
import com.wangda.alarm.service.common.tcplayer.common.WangDaContextDecoder;
import com.wangda.alarm.service.common.util.ByteBufferUtil;
import java.util.function.Consumer;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import org.springframework.stereotype.Service;

/**
 * @author wenlai
 * @version 2017-11-06
 */
@Service
public class HeartCtxDecoder extends WangDaContextDecoder<HeartContext> {

    @Override
    public MessageDecoderResult decodeData(IoSession session, IoBuffer in,
            Consumer<HeartContext> callback) {
        //session.write(new HeartMsg());
        HeartContext context = new HeartContext();
        callback.accept(context);
        return MessageDecoderResult.OK;
    }

    @Override
    public MessageDecoderResult intervalDecodeable(IoSession session, IoBuffer in) {
        byte b = ByteBufferUtil.extractByte(in, ProtocalFieldsDesc.PROTOCOL_DATA_TYPE);
        if (b == 0x0F) {
            return MessageDecoderResult.OK;
        }

        return MessageDecoderResult.NOT_OK;
    }
}
