package com.wangda.alarm.service.common.tcplayer;

import java.nio.charset.Charset;
import javax.annotation.Resource;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineDecoder;
import org.apache.mina.filter.codec.textline.TextLineEncoder;
import org.springframework.stereotype.Service;

/**
 * @author wangshuo
 * @version 2017-10-23
 */
@Service
public class MyCodeFactory /*implements ProtocolCodecFactory*/ {

    //private final TextLineEncoder encoder;
    //private final TextLineDecoder decoder;

//    @Resource
//    AlarmCtxEncoder encoder;
//
//    @Resource
//    AlarmCtxDecoder decoder;
//
//    public MyCodeFactory() {
//        //encoder = new TextLineEncoder(charset, LineDelimiter.UNIX);
//        //decoder = new TextLineDecoder(charset, LineDelimiter.AUTO);
//
//    }
//
//
//    @Override
//    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
//        return encoder;
//    }
//
//    @Override
//    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
//        return decoder;
//    }
//
//    public AlarmCtxEncoder getEncoder() {
//        return encoder;
//    }
//
//    public AlarmCtxDecoder getDecoder() {
//        return decoder;
//    }
}
