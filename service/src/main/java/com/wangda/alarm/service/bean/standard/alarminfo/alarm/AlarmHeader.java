package com.wangda.alarm.service.bean.standard.alarminfo.alarm;

import com.wangda.alarm.service.bean.standard.DataType;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
public class AlarmHeader {
    private String targetTeleCode;
    private String sourceTeleCode;
    private DataType dataType;
    private boolean zip;
    private byte version;
    private byte dataTypeCode;
    private byte subDataTypeCode;

    public String getTargetTeleCode() {
        return targetTeleCode;
    }

    public void setTargetTeleCode(String targetTeleCode) {
        this.targetTeleCode = targetTeleCode;
    }

    public String getSourceTeleCode() {
        return sourceTeleCode;
    }

    public void setSourceTeleCode(String sourceTeleCode) {
        this.sourceTeleCode = sourceTeleCode;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public boolean isZip() {
        return zip;
    }

    public void setZip(boolean zip) {
        this.zip = zip;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getDataTypeCode() {
        return dataTypeCode;
    }

    public void setDataTypeCode(byte dataTypeCode) {
        this.dataTypeCode = dataTypeCode;
    }

    public byte getSubDataTypeCode() {
        return subDataTypeCode;
    }

    public void setSubDataTypeCode(byte subDataTypeCode) {
        this.subDataTypeCode = subDataTypeCode;
    }
}
