package com.wangda.alarm.service.tcplayer.resp;

import com.wangda.alarm.service.bean.standard.DataType;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.RespHeader;
import com.wangda.alarm.service.bean.standard.protocol.ProtocalFieldsDesc;
import com.wangda.alarm.service.util.ByteBufferUtil;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import org.apache.mina.core.buffer.IoBuffer;

/**
 * @author lixiaoxiong
 * @version 2017-10-24
 */
public class RespHeaderDecoder {
    public RespHeader headerDecode(IoBuffer buffer, CharsetDecoder cd) {
        RespHeader header = new RespHeader();
        //1. 目的电报码
        try {
            header.setTargetTeleCode(ByteBufferUtil.extractString(buffer,
                    ProtocalFieldsDesc.RESP_HEADER_TARGET_TELECODE, cd));
        } catch (CharacterCodingException e) {
            header.setTargetTeleCode("unknow");
        }

        //2. 源电报码
        try {
            header.setSourceTeleCode(ByteBufferUtil.extractString(buffer,
                    ProtocalFieldsDesc.RESP_HEADER_SOURCE_TELECODE, cd));
        } catch (CharacterCodingException e) {
            header.setSourceTeleCode("unknow");
        }

        //3. 数据命令代码
        byte datacmd = ByteBufferUtil.extractByte(buffer, ProtocalFieldsDesc.RESP_HEADER_DATACMD_CODE);
        int idatacmd = ByteBufferUtil.byteToInt(datacmd);
        if (idatacmd == 0x00) {
            header.setDataType(DataType.DATA);
        } else if (idatacmd == 0x01) {
            header.setDataType(DataType.CMD);
        }

        //4. 压缩标识
        buffer.position(ProtocalFieldsDesc.RESP_HEADER_ZIP_FLAG.getPosition());
        buffer.limit(ProtocalFieldsDesc.RESP_HEADER_ZIP_FLAG.getLimit());
        byte zipflag = ByteBufferUtil.extractByte(buffer, ProtocalFieldsDesc.RESP_HEADER_ZIP_FLAG);
        int izipflag = ByteBufferUtil.byteToInt(zipflag);
        if (izipflag == 0) {
            header.setZip(false);
        } else {
            header.setZip(true);
        }
        //5. 版本码
        header.setVersion(ByteBufferUtil.extractByte(buffer, ProtocalFieldsDesc.RESP_HEADER_VERSION));

        //6. 数据类型码
        header.setDataTypeCode(ByteBufferUtil.extractByte(buffer, ProtocalFieldsDesc.RESP_HEADER_DATA_TYPE));

        //7. 数据子类型码
        header.setDataSubTypeCode(ByteBufferUtil.extractByte(buffer, ProtocalFieldsDesc.RESP_HEADER_DATA_SUBTYPE));
        return header;
    }
}
