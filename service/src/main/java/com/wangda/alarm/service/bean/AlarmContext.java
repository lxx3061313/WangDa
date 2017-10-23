package com.wangda.alarm.service.bean;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
public class AlarmContext {
    private AlarmHeader header = new AlarmHeader();
    private AlarmBody body = new AlarmBody();

    public AlarmHeader getHeader() {
        return header;
    }

    public void setHeader(AlarmHeader header) {
        this.header = header;
    }

    public AlarmBody getBody() {
        return body;
    }

    public void setBody(AlarmBody body) {
        this.body = body;
    }
}
