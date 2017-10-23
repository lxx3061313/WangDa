package com.wangda.alarm.service.bean;

import java.util.Date;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
public class AlarmBody {
    /** 压缩标志-1字节 */
    private byte zip;

    /** 数据命令-1字节 */
    private byte dataCmd;

    /**数据子命令-1字节 */
    private byte dataSubCmd;

    /** 报警级别-1字节 */
    private AlarmLevel alartLevel;

    /** 报警类型-2字节 */
    private byte[] alartType;

    /** 设备类型-2字节 */
    private byte[] deviceType;

    /** 设备序号-2字节 */
    private byte[] deviceNo;

    /** 报警时间-4字节 */
    private Date alarmTime;

    /** 恢复时间-4字节 */
    private Date recoverTime;

    /** 报警order-2字节 */
    private byte[] alarmOrder;

    /** 状态-1字节 */
    private AlarmStatus status;

    /** 报警描述字节长度-1字节 */
    private int alarmCtxLenght;

    /** 设备名称字节长度-1字节 */
    private int deviceNameLength;

    /** 报警内容描述-最多255字节 */
    private String alarmCtx;

    /** 设备名称-最多255字节 */
    private String deviceName;

    /** 检修标志-1字节 */
    private OverhaulType overhaulType;

    /** 预留内容长度-1字节 */
    private int reservedCtxLth;

    /** 预留内容 */
    private String reservedCtx;

    public byte getZip() {
        return zip;
    }

    public void setZip(byte zip) {
        this.zip = zip;
    }

    public byte getDataCmd() {
        return dataCmd;
    }

    public void setDataCmd(byte dataCmd) {
        this.dataCmd = dataCmd;
    }

    public byte getDataSubCmd() {
        return dataSubCmd;
    }

    public void setDataSubCmd(byte dataSubCmd) {
        this.dataSubCmd = dataSubCmd;
    }

    public AlarmLevel getAlartLevel() {
        return alartLevel;
    }

    public void setAlartLevel(AlarmLevel alartLevel) {
        this.alartLevel = alartLevel;
    }

    public byte[] getAlartType() {
        return alartType;
    }

    public void setAlartType(byte[] alartType) {
        this.alartType = alartType;
    }

    public byte[] getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(byte[] deviceType) {
        this.deviceType = deviceType;
    }

    public byte[] getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(byte[] deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    public Date getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(Date recoverTime) {
        this.recoverTime = recoverTime;
    }

    public byte[] getAlarmOrder() {
        return alarmOrder;
    }

    public void setAlarmOrder(byte[] alarmOrder) {
        this.alarmOrder = alarmOrder;
    }

    public AlarmStatus getStatus() {
        return status;
    }

    public void setStatus(AlarmStatus status) {
        this.status = status;
    }

    public int getAlarmCtxLenght() {
        return alarmCtxLenght;
    }

    public void setAlarmCtxLenght(int alarmCtxLenght) {
        this.alarmCtxLenght = alarmCtxLenght;
    }

    public int getDeviceNameLength() {
        return deviceNameLength;
    }

    public void setDeviceNameLength(int deviceNameLength) {
        this.deviceNameLength = deviceNameLength;
    }

    public String getAlarmCtx() {
        return alarmCtx;
    }

    public void setAlarmCtx(String alarmCtx) {
        this.alarmCtx = alarmCtx;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public OverhaulType getOverhaulType() {
        return overhaulType;
    }

    public void setOverhaulType(OverhaulType overhaulType) {
        this.overhaulType = overhaulType;
    }

    public int getReservedCtxLth() {
        return reservedCtxLth;
    }

    public void setReservedCtxLth(int reservedCtxLth) {
        this.reservedCtxLth = reservedCtxLth;
    }

    public String getReservedCtx() {
        return reservedCtx;
    }

    public void setReservedCtx(String reservedCtx) {
        this.reservedCtx = reservedCtx;
    }
}
