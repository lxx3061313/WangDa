package com.wangda.alarm.service.bean.standard.alarminfo.alarm;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
public enum  AlarmLevel {
    LEVEL_ONE((byte) 1, "1级报警"),
    LEVEL_TWO((byte)2, "2级报警"),
    LEVEL_THREE((byte)3, "3级报警"),
    WARN((byte)4, "预警");
    private byte code;
    private String desc;

    AlarmLevel(byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static AlarmLevel codeOf(byte code) {
        for (AlarmLevel level : values()) {
            if (level.code == code)
                return level;
        }
        return WARN;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
