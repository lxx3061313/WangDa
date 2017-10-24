package com.wangda.alarm.service.tcplayer;

import com.wangda.alarm.service.bean.constant.KeepAliveMsg;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-25
 */
@Service
public class KeepAliveMsgEncoder implements MessageEncoder<KeepAliveMsg> {

    @Override
    public void encode(IoSession session, KeepAliveMsg message, ProtocolEncoderOutput out)
            throws Exception {
        out.write(KeepAliveMsg.msg());
    }
}
