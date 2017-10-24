package com.wangda.alarm.service.tcplayer;

import com.wangda.alarm.service.bean.AlarmHeader;
import com.wangda.alarm.service.bean.DataType;
import com.wangda.alarm.service.bean.RespHeader;
import com.wangda.alarm.service.bean.protocol.ProtocalFieldsDesc;
import com.wangda.alarm.service.util.ByteBufferUtil;
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
        String target = buffer.getString(cd);
        header.setTargetTeleCode(target);

        //2. 源电报码
        buffer.position(ProtocalFieldsDesc.RESP_HEADER_SOURCE_TELECODE.getPosition());
        buffer.limit(ProtocalFieldsDesc.RESP_HEADER_SOURCE_TELECODE.getLimit());
        String source = buffer.getString(cd);
        header.setSourceTeleCode(source);

        //3. 数据命令代码
        buffer.position(ProtocalFieldsDesc.RESP_HEADER_DATACMD_CODE.getPosition());
        buffer.limit(ProtocalFieldsDesc.RESP_HEADER_DATACMD_CODE.getLimit());
        int datacmd = ByteBufferUtil.byteToInt(buffer.get());
        if (datacmd == 0x00) {
            header.setDataType(DataType.DATA);
        } else if (datacmd == 0x01) {
            header.setDataType(DataType.CMD);
        }

        //4. 压缩标识
        buffer.position(ProtocalFieldsDesc.RESP_HEADER_ZIP_FLAG.getPosition());
        buffer.limit(ProtocalFieldsDesc.RESP_HEADER_ZIP_FLAG.getLimit());
        header.setZip();
        //5. 版本码
        buffer.position(ProtocalFieldsDesc.RESP_HEADER_VERSION.getPosition());
        buffer.limit(ProtocalFieldsDesc.RESP_HEADER_VERSION.getLimit());

        //6. 数据类型码
        buffer.position(ProtocalFieldsDesc.RESP_HEADER_DATA_TYPE.getPosition());
        buffer.limit(ProtocalFieldsDesc.RESP_HEADER_DATA_TYPE.getLimit());

        //7. 数据子类型码
        buffer.position(ProtocalFieldsDesc.RESP_HEADER_DATA_SUBTYPE.getPosition());
        buffer.limit(ProtocalFieldsDesc.RESP_HEADER_DATA_SUBTYPE.getLimit());

        buffer.position(ProtocalFieldsDesc);
        buffer.limit(ProtocalFieldsDesc);

        return header;
    }
}
