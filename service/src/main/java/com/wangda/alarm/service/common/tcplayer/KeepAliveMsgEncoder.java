package com.wangda.alarm.service.common.tcplayer;

import com.wangda.alarm.service.bean.standard.alarminfo.heart.HeartMsg;
import com.wangda.alarm.service.bean.standard.constant.KeepAliveMsg;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-25
 */
@Service
public class KeepAliveMsgEncoder implements MessageEncoder<HeartMsg> {

    @Override
    public void encode(IoSession session, HeartMsg message, ProtocolEncoderOutput out)
            throws Exception {
        IoBuffer buffer = IoBuffer.allocate(16).setAutoExpand(true);
        buffer.put(message.getMsg());
        buffer.flip();
        out.write(buffer);
    }
}
