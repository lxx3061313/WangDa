package com.wangda.alarm.service.tcplayer;

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

    public WangDaDataCodecFactory() {
    }

    @PostConstruct
    private void init() {
        super.addMessageDecoder(alarmCtxDecoder);
        super.addMessageDecoder(faultCtxDecoder);
    }
}
