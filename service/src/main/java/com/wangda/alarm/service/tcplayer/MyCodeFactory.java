package com.wangda.alarm.service.tcplayer;

import java.nio.charset.Charset;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineDecoder;
import org.apache.mina.filter.codec.textline.TextLineEncoder;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
public class MyCodeFactory implements ProtocolCodecFactory {

    //private final TextLineEncoder encoder;
    //private final TextLineDecoder decoder;
    private final AlarmCtxEncoder encoder;
    private final AlarmCtxDecoder decoder;
    public MyCodeFactory() {
        this(Charset.forName("utf-8"));
    }
    public MyCodeFactory(Charset charset) {
        //encoder = new TextLineEncoder(charset, LineDelimiter.UNIX);
        //decoder = new TextLineDecoder(charset, LineDelimiter.AUTO);
        encoder = new AlarmCtxEncoder();
        decoder = new AlarmCtxDecoder(Charset.forName("utf-8"));
    }


    @Override
    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return encoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return decoder;
    }

    public AlarmCtxEncoder getEncoder() {
        return encoder;
    }

    public AlarmCtxDecoder getDecoder() {
        return decoder;
    }
}
