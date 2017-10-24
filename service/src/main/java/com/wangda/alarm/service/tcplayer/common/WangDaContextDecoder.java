package com.wangda.alarm.service.tcplayer.common;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

/**
 * @author lixiaoxiong
 * @version 2017-10-25
 */
public abstract class WangDaContextDecoder<T> implements MessageDecoder {

    protected CharsetDecoder cd = Charset.forName("GBK").newDecoder();

    @Override
    public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
        return null;
    }

    @Override
    public MessageDecoderResult decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
            throws Exception {
        IoBuffer buffer = IoBuffer.allocate(300).setAutoExpand(true);
        while (in.hasRemaining()) {
            byte b = in.get();
            buffer.put(b);
        }
        buffer.flip();
        T data = decodeData(session, in);
        out.write(data);
        return MessageDecoderResult.OK;
    }

    @Override
    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {

    }

    public abstract T decodeData(IoSession session, IoBuffer in);
    public abstract MessageDecoderResult intervalDecodeable(IoSession session, IoBuffer in);
}
