package com.wangda.alarm.service.tcplayer.common;

import com.wangda.alarm.service.bean.QueryAlarmParam;
import com.wangda.alarm.service.bean.constant.KeepAliveMsg;
import com.wangda.alarm.service.tcplayer.KeepAliveMsgEncoder;
import com.wangda.alarm.service.tcplayer.alarm.AlarmCtxDecoder;
import com.wangda.alarm.service.tcplayer.fault.FaultCtxDecoder;
import com.wangda.alarm.service.tcplayer.resp.QueryAlarmParamEncoder;
import com.wangda.alarm.service.tcplayer.resp.RespContextDecoder;
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

    public WangDaDataCodecFactory() {
    }

    @PostConstruct
    private void init() {
        super.addMessageDecoder(alarmCtxDecoder);
        super.addMessageDecoder(faultCtxDecoder);
        super.addMessageDecoder(contextDecoder);
        super.addMessageEncoder(KeepAliveMsg.class, keepAliveMsgEncoder);
        super.addMessageEncoder(QueryAlarmParam.class, queryAlarmParamEncoder);
    }
}
