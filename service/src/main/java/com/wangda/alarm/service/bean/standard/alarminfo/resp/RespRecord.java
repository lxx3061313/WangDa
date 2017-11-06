package com.wangda.alarm.service.bean.standard.alarminfo.resp;

import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmLevel;
import java.util.Date;

/**
 * @author lixiaoxiong
 * @version 2017-10-24
 */
public class RespRecord {

    private String stationCode;

    /**
     * 发生时间-4字节
     */
    private Date happenTime;

    /**
     * 恢复时间-4字节
     */
    private Date recoverTime;

    /**
     * 预留字段-4字节(前2个字节是报警类型)
     */
    private byte[] reserveField;

    /**
     * 设备名称长度-1字节
     */
    private byte deviceNameLth;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 报警内容长度-1字节
     */
    private byte alarmCtxLth;

    /**
     * 报警内容
     */
    private String alarmCtx;

    /**
     * 预留字段-2字节
     */
    private byte [] reserve1;

    /**
     * 预留字段-2字节
     */
    private byte [] reserve2;

    /**
     * 报警级别-1字节
     */
    private AlarmLevel level;

    /**
     * 设备类型-2字节
     */
    private byte [] deviceType;

    /**
     * 设备序号-2字节
     */
    private byte [] deviceNo;

    public Date getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(Date happenTime) {
        this.happenTime = happenTime;
    }

    public Date getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(Date recoverTime) {
        this.recoverTime = recoverTime;
    }

    public byte getDeviceNameLth() {
        return deviceNameLth;
    }

    public void setDeviceNameLth(byte deviceNameLth) {
        this.deviceNameLth = deviceNameLth;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public byte getAlarmCtxLth() {
        return alarmCtxLth;
    }

    public void setAlarmCtxLth(byte alarmCtxLth) {
        this.alarmCtxLth = alarmCtxLth;
    }

    public String getAlarmCtx() {
        return alarmCtx;
    }

    public void setAlarmCtx(String alarmCtx) {
        this.alarmCtx = alarmCtx;
    }

    public byte[] getReserve1() {
        return reserve1;
    }

    public void setReserve1(byte[] reserve1) {
        this.reserve1 = reserve1;
    }

    public byte[] getReserve2() {
        return reserve2;
    }

    public void setReserve2(byte[] reserve2) {
        this.reserve2 = reserve2;
    }

    public AlarmLevel getLevel() {
        return level;
    }

    public void setLevel(AlarmLevel level) {
        this.level = level;
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

    public byte[] getReserveField() {
        return reserveField;
    }

    public void setReserveField(byte[] reserveField) {
        this.reserveField = reserveField;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }
}
