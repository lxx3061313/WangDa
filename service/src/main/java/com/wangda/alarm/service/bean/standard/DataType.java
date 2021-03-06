package com.wangda.alarm.service.bean.standard;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
public enum DataType {
    DATA((byte)0, "数据"),
    CMD((byte)1, "命令");
    private byte code;
    private String desc;

    DataType(byte code, String desc) {
        this.code = code;
        this.desc = desc;
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
