package com.wangda.alarm.service.bean.biz;

/**
 * @author lixiaoxiong
 * @version 2017-10-25
 */
public enum UserStatus {
    NORMAL(0, "正常"),
    FREEZE(1, "冻结"),
    DEL(2, "删除");
    private int code;
    private String desc;

    UserStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
