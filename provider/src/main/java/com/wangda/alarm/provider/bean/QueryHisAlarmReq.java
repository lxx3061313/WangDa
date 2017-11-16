package com.wangda.alarm.provider.bean;

import java.util.Date;

/**
 * @author cyong
 * @version 2017-11-07
 */
public class QueryHisAlarmReq {
    private String code;
    private Date from;
    private Date to;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}
