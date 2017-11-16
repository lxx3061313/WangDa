package com.wangda.alarm.service.common.tcplayer.alarm;

import static com.sun.tools.doclets.formats.html.markup.HtmlStyle.header;

import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmHeader;
import com.wangda.alarm.service.bean.standard.DataType;
import com.wangda.alarm.service.bean.standard.protocol.ProtocalFieldsDesc;
import com.wangda.alarm.service.common.util.ByteBufferUtil;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import org.apache.mina.core.buffer.IoBuffer;
import org.springframework.stereotype.Service;

/**
 * @author wangshuo
 * @version 2017-10-24
 */
@Service
public class AlarmHeaderDecoder {

    public AlarmHeader headerDecode(IoBuffer buffer, CharsetDecoder cd)
            throws CharacterCodingException {
        //保存buffer现场
        ByteBufferUtil.storeCtx(buffer);

        try {
            //起始位置
            buffer.position(ProtocalFieldsDesc.ALARM_HEADER_TARGET_TELECODE.getPosition());

            // 目标电报码
            AlarmHeader header = new AlarmHeader();
            byte[] target = ByteBufferUtil
                    .forward(buffer, ProtocalFieldsDesc.ALARM_HEADER_TARGET_TELECODE.getByteLth());
            header.setTargetTeleCode(ByteBufferUtil.bytesToString(target, cd));

            // 源电报码
            byte[] source = ByteBufferUtil
                    .forward(buffer, ProtocalFieldsDesc.ALARM_HEADER_SOURCE_TELECODE.getByteLth());
            header.setSourceTeleCode(ByteBufferUtil.bytesToString(source, cd));

            // 数据命令
            byte datacmd = buffer.get();
            if (datacmd == 0x00) {
                header.setDataType(DataType.DATA);
            } else if (datacmd == 0x01) {
                header.setDataType(DataType.CMD);
            }

            //报警没有压缩标记
            //版本码
            header.setVersion(buffer.get());

            // 数据类型码
            header.setDataTypeCode(buffer.get());

            //数据子类型码
            header.setSubDataTypeCode(buffer.get());
            return header;
        } finally {
            ByteBufferUtil.recoverCtx(buffer);
        }
    }
}
