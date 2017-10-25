package com.wangda.alarm.service.bean.standard.alarminfo.alarm;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
public enum  AlarmLevel {
    LEVEL_ONE(1, "1级报警"),
    LEVEL_TWO(2, "2级报警"),
    LEVEL_THREE(3, "3级报警"),
    WARN(4, "预警");
    private int code;
    private String desc;

    AlarmLevel(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static AlarmLevel codeOf(int code) {
        for (AlarmLevel level : values()) {
            if (level.code == code)
                return level;
        }
        return WARN;
    }
}
