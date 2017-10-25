package com.wangda.alarm.service.common.tcplayer.alarm;

import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmContext;
import com.wangda.alarm.service.bean.standard.protocol.ProtocalFieldsDesc;
import com.wangda.alarm.service.common.tcplayer.common.WangDaContextDecoder;
import com.wangda.alarm.service.common.util.ByteBufferUtil;
import javax.annotation.Resource;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
@Service
public class AlarmCtxDecoder extends WangDaContextDecoder<AlarmContext> {

    @Resource
    AlarmDataDecoder alarmDataDecoder;

    public AlarmCtxDecoder() {
    }

    @Override
    public AlarmContext decodeData(IoSession session, IoBuffer in) {
        return alarmDataDecoder.decodeData(in, cd);
    }

    @Override
    public MessageDecoderResult intervalDecodeable(IoSession session, IoBuffer in) {
        in.position(ProtocalFieldsDesc.ALARM_HEADER_DATACMD_CODE.getPosition());
        in.limit(ProtocalFieldsDesc.ALARM_HEADER_DATACMD_CODE.getLimit());
        MessageDecoderResult result;
        byte dataCmd = in.get();
        //1. 判断是命令还是数据
        if (dataCmd == 1) {
            in.flip();
            return MessageDecoderResult.NOT_OK;
        }

        //2. 判断是否有压缩位
        in.position(ProtocalFieldsDesc.FAULT_HEADER_ZIP_FLAG.getPosition());
        in.limit(ProtocalFieldsDesc.FAULT_HEADER_ZIP_FLAG.getLimit());
        byte zipFlag = in.get();
        int iflag = ByteBufferUtil.byteToInt(zipFlag);
        //2.1 没有压缩位
        if (iflag != 0 && iflag != 1 && iflag == 0xe0) {
            result =   MessageDecoderResult.OK;
        } else {
            result =   MessageDecoderResult.NOT_OK;
        }

        in.flip();
        return result;
    }
}