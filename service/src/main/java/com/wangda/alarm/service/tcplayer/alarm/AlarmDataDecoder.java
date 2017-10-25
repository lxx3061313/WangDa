package com.wangda.alarm.service.tcplayer.alarm;

import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmContext;
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
public class AlarmDataDecoder extends WangDaTransProDecoder<AlarmContext>{

    @Resource
    AlarmHeaderDecoder headerDecoder;

    @Resource
    AlarmBodyDecoder bodyDecoder;

    @Override
    public AlarmContext decodeData(IoBuffer buffer, CharsetDecoder cd) {
        AlarmContext context = new AlarmContext();
        try {
            context.setHeader(headerDecoder.headerDecode(buffer, cd));
            context.setBody(bodyDecoder.bodyDecode(buffer, cd));
            return context;
        } catch (CharacterCodingException e) {
            return null;
        }
    }
}
