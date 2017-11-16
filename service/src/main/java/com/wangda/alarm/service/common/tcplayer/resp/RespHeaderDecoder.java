package com.wangda.alarm.service.common.tcplayer.resp;

import com.wangda.alarm.service.bean.standard.DataType;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.RespHeader;
import com.wangda.alarm.service.bean.standard.protocol.ProtocalFieldsDesc;
import com.wangda.alarm.service.common.util.ByteBufferUtil;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import org.apache.mina.core.buffer.IoBuffer;
import org.springframework.stereotype.Service;

/**
 * @author wenlai
 * @version 2017-10-24
 */
@Service
public class RespHeaderDecoder {
    public RespHeader headerDecode(IoBuffer buffer, CharsetDecoder cd) {
        ByteBufferUtil.storeCtx(buffer);
        try {
            //0. 起始位置
            buffer.position(ProtocalFieldsDesc.RESP_HEADER_TARGET_TELECODE.getPosition());
            RespHeader header = new RespHeader();
            //1. 目的电报码
            try {
                byte[] targetTelecode = ByteBufferUtil.forward(buffer,
                        ProtocalFieldsDesc.RESP_HEADER_TARGET_TELECODE.getByteLth());
                header.setTargetTeleCode(ByteBufferUtil.bytesToString(targetTelecode, cd));
            } catch (CharacterCodingException e) {
                header.setTargetTeleCode("unknow");
            }

            //2. 源电报码
            try {
                byte[] source = ByteBufferUtil.forward(buffer,
                        ProtocalFieldsDesc.RESP_HEADER_SOURCE_TELECODE.getByteLth());
                header.setSourceTeleCode(ByteBufferUtil.bytesToString(source, cd));
            } catch (CharacterCodingException e) {
                header.setSourceTeleCode("unknow");
            }

            //3. 数据命令代码
            byte[] datacmds = ByteBufferUtil
                    .forward(buffer, ProtocalFieldsDesc.RESP_HEADER_DATACMD_CODE.getByteLth());
            byte datacmd = datacmds[0];
            if (datacmd == 0x00) {
                header.setDataType(DataType.DATA);
            } else if (datacmd == 0x01) {
                header.setDataType(DataType.CMD);
            }

            //4. 压缩标识
            byte[] zip = ByteBufferUtil
                    .forward(buffer, ProtocalFieldsDesc.RESP_HEADER_ZIP_FLAG.getByteLth());
            byte izipflag = zip[0];
            if (izipflag == 0) {
                header.setZip(false);
            } else {
                header.setZip(true);
            }

            //5. 版本码
            byte[] version = ByteBufferUtil
                    .forward(buffer, ProtocalFieldsDesc.RESP_HEADER_VERSION.getByteLth());
            header.setVersion(version[0]);

            //6. 数据类型码
            byte[] datatype = ByteBufferUtil
                    .forward(buffer, ProtocalFieldsDesc.RESP_HEADER_DATA_TYPE.getByteLth());
            header.setDataTypeCode(datatype[0]);

            //7. 数据子类型码
            byte[] subtype = ByteBufferUtil
                    .forward(buffer, ProtocalFieldsDesc.RESP_HEADER_DATA_SUBTYPE.getByteLth());
            header.setDataSubTypeCode(subtype[0]);
            return header;
        } finally {
            ByteBufferUtil.recoverCtx(buffer);
        }

    }
}
