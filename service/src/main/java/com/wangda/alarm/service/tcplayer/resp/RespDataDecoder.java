package com.wangda.alarm.service.tcplayer.resp;

import com.wangda.alarm.service.bean.standard.alarminfo.resp.RespContext;
import com.wangda.alarm.service.tcplayer.common.WangDaTransProDecoder;
import java.nio.charset.CharsetDecoder;
import javax.annotation.Resource;
import org.apache.mina.core.buffer.IoBuffer;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-24
 */
@Service
public class RespDataDecoder extends WangDaTransProDecoder<RespContext> {

    @Resource
    RespHeaderDecoder headerDecoder;

    @Resource
    RespBodyDecoder bodyDecoder;

    @Override
    public RespContext decodeData(IoBuffer buffer, CharsetDecoder cd) {
        RespContext context = new RespContext();
        context.setHeader(headerDecoder.headerDecode(buffer, cd));
        context.setBody(bodyDecoder.bodyDecode(buffer, cd));
        return context;
    }
}
