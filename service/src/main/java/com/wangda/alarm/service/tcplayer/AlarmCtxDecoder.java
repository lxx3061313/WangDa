package com.wangda.alarm.service.tcplayer;

import com.wangda.alarm.service.bean.AlarmBean;
import com.wangda.alarm.service.bean.AlarmBody;
import com.wangda.alarm.service.bean.AlarmContext;
import com.wangda.alarm.service.bean.AlarmHeader;
import com.wangda.alarm.service.bean.AlarmLevel;
import com.wangda.alarm.service.bean.AlarmStatus;
import com.wangda.alarm.service.bean.BizBeanType;
import com.wangda.alarm.service.bean.DataType;
import com.wangda.alarm.service.bean.OverhaulType;
import com.wangda.alarm.service.bean.protocol.ProtocalFieldsDesc;
import com.wangda.alarm.service.util.ByteBufferUtil;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Date;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
public class AlarmCtxDecoder extends CumulativeProtocolDecoder {
    private final Charset charset;
    CharsetDecoder cd;
    public AlarmCtxDecoder(Charset charset) {
        this.charset = charset;
        cd = charset.newDecoder();
    }

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
            throws Exception {
        IoBuffer buffer = IoBuffer.allocate(200).setAutoExpand(true);
        while (in.hasRemaining()) {
            byte b = in.get();
            buffer.put(b);
        }
        buffer.flip();

        AlarmBean context = new AlarmBean();
        //1. data_len
        buffer.position(ProtocalFieldsDesc.PROTOCOL_DATA_LENGTH.getPosition());
        buffer.limit(ProtocalFieldsDesc.PROTOCOL_DATA_LENGTH.getLimit());
        context.setDataLength(buffer.getInt());
        buffer.flip();

        //2. flag of data or heart
        buffer.position(ProtocalFieldsDesc.PROTOCOL_DATA_TYPE.getPosition());
        buffer.limit(ProtocalFieldsDesc.PROTOCOL_DATA_TYPE.getLimit());
        byte flag = buffer.get();
        if (flag == 0x8f) {
            context.setBeanType(BizBeanType.DATA);
        } else if (flag == 0x0f) {
            context.setBeanType(BizBeanType.HEART);
        }

        //3. data context
        AlarmContext alarmContext = new AlarmContext();
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
        header.setTargetTeleCode(source);
        //3.3 data cmd code
        buffer.position(ProtocalFieldsDesc.ALARM_HEADER_DATACMD_CODE.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_HEADER_DATACMD_CODE.getLimit());
        byte datacmd = buffer.get();
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
        alarmContext.setHeader(header);

        //4. body
        //4.1 压缩标志
        AlarmBody body = new AlarmBody();
        buffer.position(ProtocalFieldsDesc.ALARM_BODY_ZIPFLAG.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BODY_ZIPFLAG.getLimit());
        body.setZip(buffer.get());
        //4.2 数据命令
        buffer.position(ProtocalFieldsDesc.ALARM_BODY_DATACMD.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BODY_DATACMD.getLimit());
        body.setDataCmd(buffer.get());
        //4.3 数据子命令
        buffer.position(ProtocalFieldsDesc.ALARM_BODY_DATASUMCMD.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BODY_DATASUMCMD.getLimit());
        body.setDataSubCmd(buffer.get());
        //4.4 报警级别
        buffer.position(ProtocalFieldsDesc.ALARM_BDDY_ALARM_LEVEL.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BDDY_ALARM_LEVEL.getLimit());
        body.setAlartLevel(AlarmLevel.codeOf(buffer.get()));
        //4.5 报警类型
        buffer.position(ProtocalFieldsDesc.ALARM_BODY_ALARM_TYPE.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BODY_ALARM_TYPE.getLimit());
        byte[] ctype = new byte[2];
        buffer.get(ctype);
        body.setAlartType(ctype);
        //4.6 设备类型
        buffer.position(ProtocalFieldsDesc.ALARM_BODY_DEVICE_TYPE.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BODY_DEVICE_TYPE.getLimit());
        byte[] dtype = new byte[2];
        buffer.get(dtype);
        body.setDeviceType(dtype);
        //4.7 设备序号
        buffer.position(ProtocalFieldsDesc.ALARM_BODY_DEVICE_NO.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BODY_DEVICE_NO.getLimit());
        byte[] dno = new byte[2];
        buffer.get(dno);
        body.setDeviceNo(dno);
        //4.7 报警时间
        buffer.position(ProtocalFieldsDesc.ALARM_BODY_ALARM_HTIME.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BODY_ALARM_HTIME.getLimit());
        byte [] atime = new byte[4];
        buffer.get(atime);
        int atimestamp = ByteBufferUtil.bytesToInt(atime, 0);
        body.setAlarmTime(new Date(atimestamp * 1000L));
        //4.8 恢复时间
        buffer.position(ProtocalFieldsDesc.ALARM_BODY_ALARM_RTIME.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BODY_ALARM_RTIME.getLimit());
        byte [] rtime = new byte[4];
        buffer.get(rtime);
        int rtimestamp = ByteBufferUtil.bytesToInt(rtime, 0);
        body.setRecoverTime(new Date(rtimestamp * 1000L));
        //4.9 报警order
        buffer.position(ProtocalFieldsDesc.ALARM_BODY_ALARM_ORDER.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BODY_ALARM_ORDER.getLimit());
        byte [] order = new byte[2];
        buffer.get(order);
        body.setAlarmOrder(order);
        //4.10 报警状态
        buffer.position(ProtocalFieldsDesc.ALARM_BODY_ALARM_STATUS.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BODY_ALARM_STATUS.getLimit());
        byte status = buffer.get();
        if (status == 0) {
            body.setStatus(AlarmStatus.RECOVER);
        } else if (status == 1) {
            body.setStatus(AlarmStatus.ALARM);
        }
        //4.11 报警描述长度
        buffer.position(ProtocalFieldsDesc.ALARM_BODY_CONTEXT_LENGTH.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BODY_CONTEXT_LENGTH.getLimit());
        byte ctxLth = buffer.get();
        body.setAlarmCtxLenght(ctxLth);
        //4.12 设备名称长度
        buffer.position(ProtocalFieldsDesc.ALARM_BODY_DEVICENAME_LENGTH.getPosition());
        buffer.limit(ProtocalFieldsDesc.ALARM_BODY_DEVICENAME_LENGTH.getLimit());
        byte dnamelth = buffer.get();
        body.setDeviceNameLength(dnamelth);
        //4.13 报警描述(动态长度)
        int currentLimit = buffer.limit();
        buffer.position(currentLimit);
        buffer.limit(currentLimit + ctxLth);
        body.setAlarmCtx(buffer.getString(cd));
        //4.14 设备长度
        currentLimit = buffer.limit();
        buffer.position(currentLimit);
        buffer.limit(currentLimit + dnamelth);
        body.setDeviceName(buffer.getString(cd));
        //4.15 检修标志
        currentLimit = buffer.limit();
        buffer.position(currentLimit);
        buffer.limit(currentLimit+1);
        byte haulflag = buffer.get();
        if (haulflag == 1) {
            body.setOverhaulType(OverhaulType.OVERHAUL);
        } else if (haulflag == 2) {
            body.setOverhaulType(OverhaulType.SKYLIGHT);
        } else {
            body.setOverhaulType(OverhaulType.OTHER);
        }
        //4.16 预留长度
        currentLimit = buffer.limit();
        buffer.position(currentLimit);
        buffer.limit(currentLimit + 1);
        byte reserveLth = buffer.get();
        body.setReservedCtxLth(reserveLth);
        //4.17 预留内容
        if (reserveLth != 0) {
            currentLimit = buffer.limit();
            buffer.position(currentLimit);
            buffer.limit(currentLimit+reserveLth);
            body.setReservedCtx(buffer.getString(cd));
        }
        alarmContext.setBody(body);
        context.setData(alarmContext);
        out.write(context);
        return true;
    }
}
