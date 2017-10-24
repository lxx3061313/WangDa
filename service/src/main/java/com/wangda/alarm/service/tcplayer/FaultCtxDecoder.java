package com.wangda.alarm.service.tcplayer;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * @author lixiaoxiong
 * @version 2017-10-24
 */
public class FaultCtxDecoder extends CumulativeProtocolDecoder {

    private CharsetDecoder cd;
    public FaultCtxDecoder(Charset charset) {
        cd = charset.newDecoder();
    }

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
            throws Exception {
        IoBuffer buffer = IoBuffer.allocate(100).setAutoExpand(true);
        while (in.hasRemaining()) {
            byte b = in.get();
            buffer.put(b);
        }
        buffer.flip();
        return false;
    }
}
