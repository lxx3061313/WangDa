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
        try {
            ByteBufferUtil.storeCtx(buffer);
            buffer.position(ProtocalFieldsDesc.FAULT_HEADER_TARGET_TELECODE.getPosition());

            FaultHeader header = new FaultHeader();
            //1. 目的电报码
            byte[] target = ByteBufferUtil
                    .forward(buffer, ProtocalFieldsDesc.FAULT_HEADER_TARGET_TELECODE.getByteLth());
            header.setTargetTeleCode(ByteBufferUtil.bytesToString(target, cd));

            //2. 源电报码
            byte[] source = ByteBufferUtil.forward(buffer,
                    ProtocalFieldsDesc.FAULT_HEADER_SOURCE_TELECODE.getByteLth());
            header.setSourceTeleCode(ByteBufferUtil.bytesToString(source, cd));

            //3. 数据命令代码
            int datacmd = buffer.get();
            if (datacmd == 0x00) {
                header.setDataType(DataType.DATA);
            } else if (datacmd == 0x01) {
                header.setDataType(DataType.CMD);
            }

            //4. 压缩标志
            byte zipflag = buffer.get();
            if (zipflag == 0) {
                header.setZip(false);
            } else {
                header.setZip(true);
            }

            //5. 版本码
            header.setVersion(buffer.get());
            //6. 数据类型码
            header.setDataTypeCode(buffer.get());
            //7. 数据子类型码
            header.setSubDataTypeCode(buffer.get());
            return header;
        } finally {
            ByteBufferUtil.recoverCtx(buffer);
        }
    }
}
