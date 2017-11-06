package com.wangda.alarm.service.bean.standard.protocol;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
public enum ProtocalFieldsDesc {
    PROTOCOL_DATA_LENGTH(0, 4, "数据域长度,4个字节", 4),
    PROTOCOL_DATA_TYPE(4, 5, "用来区分是数据帧还是心跳帧, 8FH数据帧, 0FH心跳帧", 1),
    ALARM_HEADER_TARGET_TELECODE(5, 8, "目的电报码", 3),
    ALARM_HEADER_SOURCE_TELECODE(8, 11, "源电报码", 3),
    ALARM_HEADER_DATACMD_CODE(11, 12, "数据/命令代码", 1),
    ALARM_HEADER_VERSION(12, 13, "版本码", 1),
    ALARM_HEADER_DATA_TYPE_CODE(13, 14, "数据类型码", 1),
    ALARM_HEADER_DATA_SUBTYPE_CODE(14, 15, "数据子类型码", 1),

    ALARM_BODY_ZIPFLAG(15, 16, "压缩标识", 1),
    ALARM_BODY_DATACMD(16, 17, "数据命令", 1),
    ALARM_BODY_DATASUMCMD(17, 18, "数据子命令", 1),
    ALARM_BDDY_ALARM_LEVEL(18, 19, "报警级别", 1),
    ALARM_BODY_ALARM_TYPE(19, 21, "报警类型", 2),
    ALARM_BODY_DEVICE_TYPE(21, 23, "设备类型", 2),
    ALARM_BODY_DEVICE_NO(23, 25, "设备序号", 2),
    ALARM_BODY_ALARM_HTIME(25, 29, "报警时间", 4),
    ALARM_BODY_ALARM_RTIME(29, 33, "报警恢复时间", 4),
    ALARM_BODY_ALARM_ORDER(33, 35, "报警order", 2),
    ALARM_BODY_ALARM_STATUS(35, 36, "报警状态", 1),
    ALARM_BODY_CONTEXT_LENGTH(36, 37, "报警描述长度,最多255字节", 1),
    ALARM_BODY_DEVICENAME_LENGTH(37, 38, "设备名称长度, 最多255字节", 1),
    ALARM_BODY_CONTEXT(38, 38+255, "报警内容, 不定长", -1),
    ALARM_BODY_DEVICE_NAME(0, 0, "设备名称, 不定长", -1),
    ALARM_BODY_OVERHAUL_FLAG(0, 0, "检修标志", 1),
    ALARM_BODY_RESERVED_LENGTH(0, 0, "预留内容长度", 1),
    ALARM_BODY_RESERVED_CONTEXT(0, 0, "预留内容", -1),


    //故障
    FAULT_HEADER_TARGET_TELECODE(5, 8, "目的电报码", 3),
    FAULT_HEADER_SOURCE_TELECODE(8, 11, "源电报码", 3),
    FAULT_HEADER_DATACMD_CODE(11, 12, "数据命令代码", 1),
    FAULT_HEADER_ZIP_FLAG(12, 13, "压缩标记", 1),
    FAULT_HEADER_VERSION(13, 14, "版本码", 1),
    FAULT_HEADER_DATA_TYPE(14, 15, "数据类型码", 1),
    FAULT_HEADER_DATA_SUBTYPE(15, 16, "数据子类型码", 1),
    FAULT_BODY_REALTIME_FLAG(16, 17, "实时数据", 1),
    FAULT_BODY_RECORD_NUM(17, 19, "记录数", 2),
    FAULT_BODY_NOC_TIME(19, 23, "通知时间", 4),
    FAULT_BODY_PROC_TIME(23, 27, "受理时间", 4),
    FAULT_BODY_RECOVER_TIME(27, 31, "恢复时间", 4),
    FAULT_BODY_RESERVE_TIME(31, 35, "预留时间", 4),
    FAULT_BODY_REASON_LENGTH(35, 36, "故障原因长度", 1),
    FAULT_BODY_REASON(36, 0, "故障原因", -1),


    //回执
    RESP_HEADER_TARGET_TELECODE(5, 8 ,"目的电报码", 3),
    RESP_HEADER_SOURCE_TELECODE(8, 11, "源电报码", 3),
    RESP_HEADER_DATACMD_CODE(11, 12, "数据命令代码", 1),
    RESP_HEADER_ZIP_FLAG(12, 13, "压缩标记", 1),
    RESP_HEADER_VERSION(13, 14, "版本码", 1),
    RESP_HEADER_DATA_TYPE(14, 15, "数据类型码", 1),
    RESP_HEADER_DATA_SUBTYPE(15, 16, "数据子类型码", 1),
    RESP_BODY_DATA_NAME(16, 17, "数据命名", 1),
    RESP_BODY_RECORD_NUM(17, 19, "记录数", 2),
    RESP_BODY_HAPPEN_TIME(0, 0, "发生时间", 4),
    RESP_BODY_RECOVER_TIME(0, 0, "恢复时间", 4),
    RESP_BODY_RESERVE_FIELD(0, 0, "预留时间", 4),
    RESP_BODY_DEVICE_NAMELTH(0, 0, "设备名称长度", 1),
    RESP_BODY_DEVICE_NAME(0, 0, "设备名称", -1),
    RESP_BODY_ALARM_CTXLTH(0, 0, "报警内容长度", 1),
    RESP_BODY_ALARM_CTX(0, 0, "报警内容", -1),
    RESP_BODY_RESERVE1(0, 0, "预留长度", 2),
    /** 下面三个字段是预留内容 */
    RESP_BODY_DEVICE_TYPE(0, 0, "设备类型", 2),
    RESP_BODY_DEVICE_NO(0, 0, "设备编号", 2),
    RESP_BODY_ALARM_LEVEL(0, 0, "报警级别", 1),
    RESP_BODY_ALARM_STATION_CODE(0,0,"报警车站编码", 3);
    private int position;
    private int limit;
    private String desc;
    private int byteLth;

    ProtocalFieldsDesc(int position, int limit, String desc, int byteLth) {
        this.position = position;
        this.limit = limit;
        this.desc = desc;
        this.byteLth = byteLth;
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

    public int getByteLth() {
        return byteLth;
    }

    public void setByteLth(int byteLth) {
        this.byteLth = byteLth;
    }
}
