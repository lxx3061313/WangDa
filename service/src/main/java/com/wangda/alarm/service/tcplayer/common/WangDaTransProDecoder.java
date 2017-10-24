package com.wangda.alarm.service.tcplayer.common;

import com.wangda.alarm.service.bean.BizBeanType;
import com.wangda.alarm.service.bean.RespContext;
import com.wangda.alarm.service.bean.WangDaBizBean;
import com.wangda.alarm.service.bean.protocol.ProtocalFieldsDesc;
import com.wangda.alarm.service.util.ByteBufferUtil;
import java.nio.charset.CharsetDecoder;
import org.apache.mina.core.buffer.IoBuffer;
import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * @author lixiaoxiong
 * @version 2017-10-24
 */
public abstract class WangDaTransProDecoder<T> {

    public static WangDaBizBean decoderProtocol(IoBuffer buffer, CharsetDecoder cd) {
        WangDaBizBean context = new WangDaBizBean();
        //1. data_len
        buffer.position(ProtocalFieldsDesc.PROTOCOL_DATA_LENGTH.getPosition());
        buffer.limit(ProtocalFieldsDesc.PROTOCOL_DATA_LENGTH.getLimit());
        context.setDataLength(ByteBufferUtil.IobufferToInt(buffer));

        //2. flag of data or heart
        buffer.position(ProtocalFieldsDesc.PROTOCOL_DATA_TYPE.getPosition());
        buffer.limit(ProtocalFieldsDesc.PROTOCOL_DATA_TYPE.getLimit());
        int i = ByteBufferUtil.byteToInt(buffer.get());
        if (i == 0x8f) {
            context.setBeanType(BizBeanType.DATA);
        } else if (i == 0x0f) {
            context.setBeanType(BizBeanType.HEART);
        }
        context.setData(decoderProtocol(buffer, cd));
        return context;
    }

    public abstract T decodeData(IoBuffer buffer, CharsetDecoder cd);
}
