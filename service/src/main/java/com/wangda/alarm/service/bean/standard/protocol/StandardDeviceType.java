package com.wangda.alarm.service.bean.standard.protocol;

/**
 * @author lixiaoxiong
 * @version 2017-10-27
 */
public enum StandardDeviceType {
    ZNGD(0x01, "站内轨道"),
    QJGD(0x02, "区间轨道"),
    DC(0x03, "道岔"),
    ZNXHJ(0x04, "站内信号机"),
    QJXHJ(0x05, "区间信号机"),
    ZJBS(0x06, "站间闭塞"),
    LSDW(0x07, "零散灯位"),
    DYP(0x08, "电源屏"),
    WDW(0x09, "外电网"),
    HJJC(0x0A, "环境监测"),
    DLJY(0x0B, "电缆绝缘"),
    DYLL(0x0C, "电源漏流"),
    ZNJK(0x0D, "智能接口");
    private int code;
    private String desc;

    StandardDeviceType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
