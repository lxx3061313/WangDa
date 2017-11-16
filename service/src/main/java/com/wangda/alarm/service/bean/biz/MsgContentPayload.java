package com.wangda.alarm.service.bean.biz;

/**
 * @author lixiaoxiong
 * @version 2017-11-16
 */
public class MsgContentPayload {
    private MsgContentAps aps = new MsgContentAps();

    public MsgContentAps getAps() {
        return aps;
    }

    public void setAps(MsgContentAps aps) {
        this.aps = aps;
    }
}
