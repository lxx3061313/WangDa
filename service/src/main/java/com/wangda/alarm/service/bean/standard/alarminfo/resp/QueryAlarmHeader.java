package com.wangda.alarm.service.bean.standard.alarminfo.resp;

/**
 * @author lixiaoxiong
 * @version 2017-10-25
 */
public class QueryAlarmHeader {

    /**
     * 目标电报码
     */
    private String targetTeleCode;

    /**
     * 源电报码
     */
    private String sourceTeleCode;

    /**
     * 数据命令
     */
    private byte datacmd;

    /**
     * 压缩标识
     */
    private byte zipflag;

    /**
     * 版本码
     */
    private byte version;

    /**
     * 数据类型
     */
    private byte dataType;

    /**
     * 数据子类型
     */
    private byte datasubType;

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

    public byte getDatacmd() {
        return datacmd;
    }

    public void setDatacmd(byte datacmd) {
        this.datacmd = datacmd;
    }

    public byte getZipflag() {
        return zipflag;
    }

    public void setZipflag(byte zipflag) {
        this.zipflag = zipflag;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getDataType() {
        return dataType;
    }

    public void setDataType(byte dataType) {
        this.dataType = dataType;
    }

    public byte getDatasubType() {
        return datasubType;
    }

    public void setDatasubType(byte datasubType) {
        this.datasubType = datasubType;
    }
}
