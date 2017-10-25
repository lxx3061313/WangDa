package com.wangda.alarm.service.common.tcplayer.alarm;

import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmHeader;
import com.wangda.alarm.service.bean.standard.DataType;
import com.wangda.alarm.service.bean.standard.protocol.ProtocalFieldsDesc;
import com.wangda.alarm.service.common.util.ByteBufferUtil;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import org.apache.mina.core.buffer.IoBuffer;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-24
 */
@Service
public class AlarmHeaderDecoder {
    public AlarmHeader headerDecode(IoBuffer buffer, CharsetDecoder cd)
            throws CharacterCodingException {
        AlarmHeader header = new AlarmHeader();
        //3.1 target tele code
        buffer.position(ProtocalFieldsDesc.ALARM_HEADER_TARGET_TELECODE.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_HEADER_TARGET_TELECODE.getLimit());
        String target = buffer.getString(cd);
        header.setTargetTeleCode(target);
        //3.2 source tele code
        buffer.position(ProtocalFieldsDesc.ALARM_HEADER_SOURCE_TELECODE.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_HEADER_SOURCE_TELECODE.getLimit());
        String source = buffer.getString(cd);
        header.setSourceTeleCode(source);
        //3.3 data cmd code
        buffer.position(ProtocalFieldsDesc.ALARM_HEADER_DATACMD_CODE.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_HEADER_DATACMD_CODE.getLimit());
        int datacmd = ByteBufferUtil.byteToInt(buffer.get());
        if (datacmd == 0x00) {
            header.setDataType(DataType.DATA);
        } else if (datacmd == 0x01) {
            header.setDataType(DataType.CMD);
        }
        //3.4 报警没有压缩标记
        //3.5 版本码
        buffer.position(ProtocalFieldsDesc.ALARM_HEADER_VERSION.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_HEADER_VERSION.getLimit());
        header.setVersion(buffer.get());
        //3.4 数据类型码
        buffer.position(ProtocalFieldsDesc.ALARM_HEADER_DATA_TYPE_CODE.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_HEADER_DATA_TYPE_CODE.getLimit());
        header.setDataTypeCode(buffer.get());
        //3.5 数据子类型码
        buffer.position(ProtocalFieldsDesc.ALARM_HEADER_DATA_SUBTYPE_CODE.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_HEADER_DATA_SUBTYPE_CODE.getLimit());
        header.setSubDataTypeCode(buffer.get());
        return header;
    }
}
