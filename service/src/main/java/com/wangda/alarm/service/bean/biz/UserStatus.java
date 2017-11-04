package com.wangda.alarm.service.bean.biz;

/**
 * @author lixiaoxiong
 * @version 2017-10-25
 */
public enum UserStatus {
    NORMAL(1, "正常"),
    FREEZE(2, "冻结"),
    DEL(3, "删除");
    private int code;
    private String desc;

    UserStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static UserStatus codeOf(int code) {
        for (UserStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
}
