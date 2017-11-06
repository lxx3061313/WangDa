package com.wangda.alarm.service.bean.standard.alarminfo.heart;

import com.wangda.alarm.service.bean.standard.constant.KeepAliveMsg;

/**
 * @author lixiaoxiong
 * @version 2017-11-06
 */
public class HeartMsg {
    private byte[] msg = KeepAliveMsg.msg();

    public byte[] getMsg() {
        return msg;
    }

    public void setMsg(byte[] msg) {
        this.msg = msg;
    }
}
