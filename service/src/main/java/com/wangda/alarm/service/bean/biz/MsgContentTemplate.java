package com.wangda.alarm.service.bean.biz;

/**
 * @author lixiaoxiong
 * @version 2017-11-16
 */
public class MsgContentTemplate {
    private MsgContentPayload payload = new MsgContentPayload();
    private MsgContentExtra extra = new MsgContentExtra();

    public MsgContentExtra getExtra() {
        return extra;
    }

    public void setExtra(MsgContentExtra extra) {
        this.extra = extra;
    }

    public MsgContentPayload getPayload() {
        return payload;
    }

    public void setPayload(MsgContentPayload payload) {
        this.payload = payload;
    }
}
