package com.wangda.alarm.service.bean.standard.alarminfo.resp;

/**
 * @author lixiaoxiong
 * @version 2017-10-24
 */
public class RespContext {
    RespHeader header;
    RespBody body;

    public RespHeader getHeader() {
        return header;
    }

    public void setHeader(RespHeader header) {
        this.header = header;
    }

    public RespBody getBody() {
        return body;
    }

    public void setBody(RespBody body) {
        this.body = body;
    }
}
