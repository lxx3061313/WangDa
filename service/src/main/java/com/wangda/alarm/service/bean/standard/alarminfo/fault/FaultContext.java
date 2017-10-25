package com.wangda.alarm.service.bean.standard.alarminfo.fault;

/**
 * @author lixiaoxiong
 * @version 2017-10-24
 */
public class FaultContext {
    FaultHeader header;
    FaultBody body;

    public FaultHeader getHeader() {
        return header;
    }

    public void setHeader(FaultHeader header) {
        this.header = header;
    }

    public FaultBody getBody() {
        return body;
    }

    public void setBody(FaultBody body) {
        this.body = body;
    }
}
