package com.wangda.alarm.service.bean.standard;

/**
 * @author lixiaoxiong
 * @version 2017-10-28
 */
public enum DataTypeCode {
    QUERY_CMD((byte)0x1A, (byte)0xFF),
    QUERY_RESP((byte)0x1A, (byte)0xFF),
    FAULT_NOC((byte)0x1A, (byte)0x06),
    ALARM_DATA((byte)0x40, (byte)0x07);
    private byte dataType;
    private byte dataSubType;

    DataTypeCode(byte dataType, byte dataSubType) {
        this.dataType = dataType;
        this.dataSubType = dataSubType;
    }

    public byte getDataType() {
        return dataType;
    }

    public void setDataType(byte dataType) {
        this.dataType = dataType;
    }

    public byte getDataSubType() {
        return dataSubType;
    }

    public void setDataSubType(byte dataSubType) {
        this.dataSubType = dataSubType;
    }
}
