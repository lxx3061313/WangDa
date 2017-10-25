package com.wangda.alarm.service.bean.standard.alarminfo.resp;

/**
 * @author lixiaoxiong
 * @version 2017-10-25
 */
public class QueryAlarmData {
    QueryAlarmHeader header;
    QueryAlarmBody body;

    public QueryAlarmHeader getHeader() {
        return header;
    }

    public void setHeader(QueryAlarmHeader header) {
        this.header = header;
    }

    public QueryAlarmBody getBody() {
        return body;
    }

    public void setBody(QueryAlarmBody body) {
        this.body = body;
    }
}
