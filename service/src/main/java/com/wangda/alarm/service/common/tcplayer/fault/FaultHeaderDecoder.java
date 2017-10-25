package com.wangda.alarm.service.common.tcplayer.fault;

import com.wangda.alarm.service.bean.standard.DataType;
import com.wangda.alarm.service.bean.standard.alarminfo.fault.FaultHeader;
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
public class FaultHeaderDecoder {
    public FaultHeader headerDecode(IoBuffer buffer, CharsetDecoder cd)
            throws CharacterCodingException {
        FaultHeader header = new FaultHeader();
        //1. 目的电报码
        buffer.position(ProtocalFieldsDesc.FAULT_HEADER_TARGET_TELECODE.getPosition());
        buffer.limit(ProtocalFieldsDesc.FAULT_HEADER_TARGET_TELECODE.getLimit());
        String target = buffer.getString(cd);
        header.setTargetTeleCode(target);
        //2. 源电报码
        buffer.position(ProtocalFieldsDesc.FAULT_HEADER_SOURCE_TELECODE.getPosition());
        buffer.limit(ProtocalFieldsDesc.FAULT_HEADER_SOURCE_TELECODE.getLimit());
        String source = buffer.getString(cd);
        header.setSourceTeleCode(source);
        //3. 数据命令代码
        buffer.position(ProtocalFieldsDesc.FAULT_HEADER_DATACMD_CODE.getPosition());
        buffer.limit(ProtocalFieldsDesc.FAULT_HEADER_DATACMD_CODE.getLimit());
        int datacmd = ByteBufferUtil.byteToInt(buffer.get());
        if (datacmd == 0x00) {
            header.setDataType(DataType.DATA);
        } else if (datacmd == 0x01) {
            header.setDataType(DataType.CMD);
        }
        //4. 压缩标志
        buffer.position(ProtocalFieldsDesc.FAULT_HEADER_ZIP_FLAG.getPosition());
        buffer.limit(ProtocalFieldsDesc.FAULT_HEADER_ZIP_FLAG.getLimit());
        byte zipflag = buffer.get();
        if (zipflag == 0) {
            header.setZip(false);
        } else {
            header.setZip(true);
        }
        //5. 版本码
        buffer.position(ProtocalFieldsDesc.FAULT_HEADER_VERSION.getPosition());
        buffer.limit(ProtocalFieldsDesc.FAULT_HEADER_VERSION.getLimit());
        header.setVersion(buffer.get());
        //6. 数据类型码
        buffer.position(ProtocalFieldsDesc.FAULT_HEADER_DATA_TYPE.getPosition());
        buffer.limit(ProtocalFieldsDesc.FAULT_HEADER_DATA_TYPE.getLimit());
        header.setDataTypeCode(buffer.get());
        //7. 数据子类型码
        buffer.position(ProtocalFieldsDesc.FAULT_HEADER_DATA_SUBTYPE.getPosition());
        buffer.limit(ProtocalFieldsDesc.FAULT_HEADER_DATA_SUBTYPE.getLimit());
        header.setSubDataTypeCode(buffer.get());
        return header;
    }
}
