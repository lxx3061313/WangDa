package com.wangda.alarm.service.tcplayer;

import com.wangda.alarm.service.bean.AlarmHeader;
import com.wangda.alarm.service.bean.RespHeader;
import com.wangda.alarm.service.bean.protocol.ProtocalFieldsDesc;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import org.apache.mina.core.buffer.IoBuffer;

/**
 * @author lixiaoxiong
 * @version 2017-10-24
 */
public class RespHeaderDecoder {
    public RespHeader headerDecode(IoBuffer buffer, CharsetDecoder cd)
            throws CharacterCodingException {
        RespHeader header = new RespHeader();
        //1. 目的电报码
        buffer.position(ProtocalFieldsDesc.RESP_HEADER_TARGET_TELECODE.getPosition());
        buffer.limit(ProtocalFieldsDesc.RESP_HEADER_TARGET_TELECODE.getLimit());

        //2. 源电报码
        buffer.position(ProtocalFieldsDesc.RESP_HEADER_SOURCE_TELECODE.getPosition());
        buffer.limit(ProtocalFieldsDesc.RESP_HEADER_SOURCE_TELECODE.getLimit());

        //3. 数据命令代码
        buffer.position(ProtocalFieldsDesc);
        buffer.limit(ProtocalFieldsDesc);
        buffer.position(ProtocalFieldsDesc);
        buffer.limit(ProtocalFieldsDesc);
        buffer.position(ProtocalFieldsDesc);
        buffer.limit(ProtocalFieldsDesc);
        buffer.position(ProtocalFieldsDesc);
        buffer.limit(ProtocalFieldsDesc);
        buffer.position(ProtocalFieldsDesc);
        buffer.limit(ProtocalFieldsDesc);
        buffer.position(ProtocalFieldsDesc);
        buffer.limit(ProtocalFieldsDesc);

        return header;
    }
}
