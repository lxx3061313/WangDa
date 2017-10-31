package com.wangda.alarm.service.bean.standard;

/**
 * @author lixiaoxiong
 * @version 2017-10-30
 */
public enum DeptType {
    SECTION(0, "电务处"),
    SEGMENT(1, "电务段"),
    WORK_SHOP(2, "车间"),
    WORK_AREA(3, "工区"),
    STATION(4, "车站");
    private int code;
    private String desc;

    DeptType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
