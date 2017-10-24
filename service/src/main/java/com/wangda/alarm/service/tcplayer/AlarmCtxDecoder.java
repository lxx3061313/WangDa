package com.wangda.alarm.service.tcplayer;

import com.wangda.alarm.service.bean.AlarmBean;
import com.wangda.alarm.service.bean.AlarmBody;
import com.wangda.alarm.service.bean.AlarmContext;
import com.wangda.alarm.service.bean.AlarmHeader;
import com.wangda.alarm.service.bean.AlarmLevel;
import com.wangda.alarm.service.bean.AlarmStatus;
import com.wangda.alarm.service.bean.BizBeanType;
import com.wangda.alarm.service.bean.DataType;
import com.wangda.alarm.service.bean.OverhaulType;
import com.wangda.alarm.service.bean.protocol.ProtocalFieldsDesc;
import com.wangda.alarm.service.util.ByteBufferUtil;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Date;
import javax.annotation.Resource;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
@Service
public class AlarmCtxDecoder extends CumulativeProtocolDecoder {

    @Resource
    AlarmDataDecoder alarmDataDecoder;

    CharsetDecoder cd;
    public AlarmCtxDecoder(Charset charset) {
        cd = charset.newDecoder();
    }

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
            throws Exception {
        IoBuffer buffer = IoBuffer.allocate(200).setAutoExpand(true);
        while (in.hasRemaining()) {
            byte b = in.get();
            buffer.put(b);
        }
        buffer.flip();
        AlarmContext context = alarmDataDecoder.decodeData(buffer, cd);
        out.write(context);
        return false;
    }
}
