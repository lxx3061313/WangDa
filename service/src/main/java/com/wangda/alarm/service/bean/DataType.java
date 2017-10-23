package com.wangda.alarm.service.bean;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
public enum  DataType {
    DATA(0, "数据"),
    CMD(0, "命令");
    private int code;
    private String desc;

    DataType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
