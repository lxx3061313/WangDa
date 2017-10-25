package com.wangda.alarm.service.bean.standard.alarminfo.alarm;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
public enum AlarmStatus {
    RECOVER(0, "恢复"),
    ALARM(1, "报警");
    private int code;
    private String desc;

    AlarmStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
