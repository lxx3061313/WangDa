package com.wangda.alarm.service.bean.standard.protocol;

/**
 * @author lixiaoxiong
 * @version 2017-10-28
 */
public enum SegmentCode {
    CDD("CCa", "成都电务段"),
    CQD("CQa", "重庆电务段"),
    GYD("GGa", "贵阳电务段"),
    DZD("DDa", "达州电务段"),
    GYBD("GBv", "贵阳北段");
    private String code;
    private String desc;

    SegmentCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
