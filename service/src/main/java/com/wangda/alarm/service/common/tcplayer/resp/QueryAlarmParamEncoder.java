package com.wangda.alarm.service.common.tcplayer.resp;

import com.wangda.alarm.service.bean.standard.BizBeanType;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.QueryAlarmBody;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.QueryAlarmHeader;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.QueryAlarmParam;
import com.wangda.alarm.service.common.util.ByteBufferUtil;
import java.util.List;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-25
 */
@Service
public class QueryAlarmParamEncoder implements MessageEncoder<QueryAlarmParam> {

    @Override
    public void encode(IoSession session, QueryAlarmParam message, ProtocolEncoderOutput out)
            throws Exception {
        IoBuffer buffer = IoBuffer.allocate(50).setAutoExpand(true);
        //1. 数据域长度
        byte[] dataLth = ByteBufferUtil.intToBytes(message.getDataLength());
        buffer.put(dataLth);

        //2. 数据心跳标识
        if (message.getBeanType() == BizBeanType.DATA) {
            buffer.put((byte) 143);
        } else {
            buffer.put((byte) 15);
        }

        //3. Data-header域
        //3.1 目的电报码
        QueryAlarmHeader header = message.getData().getHeader();
        buffer.put(ByteBufferUtil.stringToBytes(header.getTargetTeleCode(), 3));
        //3.2 源电报码
        buffer.put(ByteBufferUtil.stringToBytes(header.getSourceTeleCode(), 3));
        //3.3 数据命令码
        buffer.put(header.getDatacmd());
        //3.4 压缩标识
        buffer.put(header.getZipflag());
        //3.5 版本码
        buffer.put(header.getVersion());
        //3.6 数据类型
        buffer.put(header.getDataType());
        //3.7 数据子类型
        buffer.put(header.getDatasubType());

        //4. body
        //4.1 数据命名
        QueryAlarmBody body = message.getData().getBody();
        buffer.put(body.getDataname());
        //4.2 开始时间
        buffer.put(ByteBufferUtil.dateToBytes(body.getStarttime()));
        //4.3 结束时间
        buffer.put(ByteBufferUtil.dateToBytes(body.getEndTime()));
        //4.4 报警类型数
        buffer.put(ByteBufferUtil.shortToBytes(body.getAlarmTypeNum()));
        //4.5 报警类型
        List<Byte> alarmTypes = body.getAlarmTypes();
        for (Byte b : alarmTypes) {
            buffer.put(b);
        }
        buffer.flip();
        out.write(buffer);
    }
}
