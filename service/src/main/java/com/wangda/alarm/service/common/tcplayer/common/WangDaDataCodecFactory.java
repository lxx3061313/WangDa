package com.wangda.alarm.service.common.tcplayer.common;

import com.wangda.alarm.service.bean.standard.alarminfo.heart.HeartMsg;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.QueryAlarmParam;
import com.wangda.alarm.service.bean.standard.constant.KeepAliveMsg;
import com.wangda.alarm.service.common.tcplayer.KeepAliveMsgEncoder;
import com.wangda.alarm.service.common.tcplayer.alarm.AlarmCtxDecoder;
import com.wangda.alarm.service.common.tcplayer.fault.FaultCtxDecoder;
import com.wangda.alarm.service.common.tcplayer.heart.HeartCtxDecoder;
import com.wangda.alarm.service.common.tcplayer.resp.QueryAlarmParamEncoder;
import com.wangda.alarm.service.common.tcplayer.resp.RespContextDecoder;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-24
 */
@Service
public class WangDaDataCodecFactory extends DemuxingProtocolCodecFactory {

    @Resource
    AlarmCtxDecoder alarmCtxDecoder;

    @Resource
    FaultCtxDecoder faultCtxDecoder;

    @Resource
    RespContextDecoder contextDecoder;

    @Resource
    KeepAliveMsgEncoder keepAliveMsgEncoder;

    @Resource
    QueryAlarmParamEncoder queryAlarmParamEncoder;

    @Resource
    HeartCtxDecoder heartCtxDecoder;

    public WangDaDataCodecFactory() {
    }

    @PostConstruct
    private void init() {
        super.addMessageDecoder(alarmCtxDecoder);
        super.addMessageDecoder(faultCtxDecoder);
        super.addMessageDecoder(heartCtxDecoder);
        super.addMessageDecoder(contextDecoder);
        super.addMessageEncoder(HeartMsg.class, keepAliveMsgEncoder);
        super.addMessageEncoder(QueryAlarmParam.class, queryAlarmParamEncoder);
    }
}
