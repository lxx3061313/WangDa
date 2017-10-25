package com.wangda.alarm.service.tcplayer.resp;

import com.wangda.alarm.service.bean.standard.alarminfo.resp.RespContext;
import com.wangda.alarm.service.tcplayer.common.WangDaContextDecoder;
import javax.annotation.Resource;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-25
 */
@Service
public class RespContextDecoder extends WangDaContextDecoder<RespContext> {

    @Resource
    RespDataDecoder respDataDecoder;

    public RespContextDecoder() {
    }

    @Override
    public RespContext decodeData(IoSession session, IoBuffer in) {
        return respDataDecoder.decodeData(in, cd);
    }

    @Override
    public MessageDecoderResult intervalDecodeable(IoSession session, IoBuffer in) {
        return null;
    }
}
