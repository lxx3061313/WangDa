package com.wangda.alarm.service.bean;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
public enum  AlarmLevel {
    LEVEL_ONE(1, "1级报警"),
    LEVEL_TWO(1, "2级报警"),
    LEVEL_THREE(1, "3级报警"),
    WARN(4, "预警");
    private int code;
    private String desc;

    AlarmLevel(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
