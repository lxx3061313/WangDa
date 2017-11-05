package com.wangda.alarm.service.bean.biz;

/**
 * @author lixiaoxiong
 * @version 2017-11-05
 */
public enum OpLogType {
    LOG_IN_OUT(0, "登录登出日志"),
    WATCH_INFO(1, "查看信息"),
    OPERATE_PASSWORD(2, "操作密码日志");
    private int code;
    private String desc;

    OpLogType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static OpLogType nameOf(String name) {
        for (OpLogType type : values()) {
            if (type.name().equals(name)) {
                return type;
            }
        }
        return null;
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
