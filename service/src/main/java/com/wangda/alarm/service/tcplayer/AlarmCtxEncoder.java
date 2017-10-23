package com.wangda.alarm.service.tcplayer;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * @author lixiaoxiong
 * @version 2017-10-24
 */
public class AlarmCtxEncoder extends ProtocolEncoderAdapter {

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out)
            throws Exception {

    }
}
