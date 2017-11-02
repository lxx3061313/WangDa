package com.wangda.alarm.provider.bean;

/**
 * @author lixiaoxiong
 * @version 2017-11-01
 */
public class AlarmOutlineVo {
    private String segmentCode;
    private String segmentName;
    private String workshopCode;
    private String workshopName;
    private String workareaCode;
    private String workareaName;
    private String stationCode;
    private String stationName;
    private String alarmLevel;
    private String alarmContext;
    private int alarmCount;
    private String deviceName;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getAlarmContext() {
        return alarmContext;
    }

    public void setAlarmContext(String alarmContext) {
        this.alarmContext = alarmContext;
    }

    public int getAlarmCount() {
        return alarmCount;
    }

    public void setAlarmCount(int alarmCount) {
        this.alarmCount = alarmCount;
    }

    public String getSegmentCode() {
        return segmentCode;
    }

    public void setSegmentCode(String segmentCode) {
        this.segmentCode = segmentCode;
    }

    public String getSegmentName() {
        return segmentName;
    }

    public void setSegmentName(String segmentName) {
        this.segmentName = segmentName;
    }

    public String getWorkshopCode() {
        return workshopCode;
    }

    public void setWorkshopCode(String workshopCode) {
        this.workshopCode = workshopCode;
    }

    public String getWorkareaName() {
        return workareaName;
    }

    public void setWorkareaName(String workareaName) {
        this.workareaName = workareaName;
    }

    public String getWorkareaCode() {
        return workareaCode;
    }

    public void setWorkareaCode(String workareaCode) {
        this.workareaCode = workareaCode;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getWorkshopName() {
        return workshopName;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }
}
