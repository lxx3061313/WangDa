package com.wangda.alarm.service.util.pojo.json;

public class ErrorJsonV1 {

    public final boolean ret = false;
    public final int errcode;
    public final String errmsg;

    public ErrorJsonV1(int errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }
}
