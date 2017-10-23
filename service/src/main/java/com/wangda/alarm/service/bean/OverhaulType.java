package com.wangda.alarm.service.bean;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
public enum OverhaulType {
    OVERHAUL(1, "检修"),
    SKYLIGHT(2, "天窗修"),
    OTHER(3, "其他");
    private int code;
    private String desc;

    OverhaulType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
