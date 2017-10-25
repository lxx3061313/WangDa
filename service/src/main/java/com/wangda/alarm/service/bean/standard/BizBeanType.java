package com.wangda.alarm.service.bean.standard;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
public enum BizBeanType {
    DATA(0, "数据帧"),
    HEART(1, "心跳");
    private int code;
    private String desc;

    BizBeanType(int code, String desc) {
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
