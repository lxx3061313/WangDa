package com.wangda.alarm.service.bean.standard;

/**
 * @author lixiaoxiong
 * @version 2017-10-28
 */
public enum VersionCode {
    E_ZERO((byte) 0xE0, "指服务器或终端向站机取数据"),
    E_ONE((byte)0xE1, "终端向服务器取数据"),
    E_TWO((byte)0xE2, "指服务器向站机发送的验证数据");
    private byte code;
    private String desc;

    VersionCode(byte code, String desc) {
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
