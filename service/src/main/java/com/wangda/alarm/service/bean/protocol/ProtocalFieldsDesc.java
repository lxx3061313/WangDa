package com.wangda.alarm.service.bean.protocol;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
public enum ProtocalFieldsDesc {
    PROTOCOL_DATA_LENGTH(0, 4, "数据域长度,4个字节"),
    PROTOCOL_DATA_TYPE(4, 5, "用来区分是数据帧还是心跳帧, 8FH数据帧, 0FH心跳帧"),
    ALARM_HEADER_TARGET_TELECODE(5, 8, "目的电报码"),
    ALARM_HEADER_SOURCE_TELECODE(8, 11, "源电报码"),
    ALARM_HEADER_DATACMD_CODE(11, 12, "数据/命令代码"),
    //报警么有压缩标识
    ALARM_HEADER_VERSION(12, 13, "版本码"),
    ALARM_HEADER_DATA_TYPE_CODE(13, 14, "数据类型码"),
    ALARM_HEADER_DATA_SUBTYPE_CODE(14, 15, "数据子类型码");
    private int position;
    private int limit;
    private String desc;

    ProtocalFieldsDesc(int position, int limit, String desc) {
        this.position = position;
        this.limit = limit;
        this.desc = desc;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
