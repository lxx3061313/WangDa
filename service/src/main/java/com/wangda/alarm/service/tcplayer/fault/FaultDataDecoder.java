package com.wangda.alarm.service.tcplayer.fault;

import com.wangda.alarm.service.bean.FaultContext;
import com.wangda.alarm.service.tcplayer.common.WangDaTransProDecoder;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import javax.annotation.Resource;
import org.apache.mina.core.buffer.IoBuffer;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-24
 */
@Service
public class FaultDataDecoder extends WangDaTransProDecoder<FaultContext> {

    @Resource
    FaultHeaderDecoder headerDecoder;

    @Resource
    FaultBodyDecoder bodyDecoder;

    @Override
    public FaultContext decodeData(IoBuffer buffer, CharsetDecoder cd) {
        FaultContext context = new FaultContext();
        try {
            context.setHeader(headerDecoder.headerDecode(buffer, cd));
            context.setBody(bodyDecoder.bodyDecode(buffer, cd));
            return context;
        } catch (CharacterCodingException e) {
            return null;
        }
    }
}
