package com.wangda.alarm.service.bean.biz;

import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmLevel;

/**
 * @author lixiaoxiong
 * @version 2017-11-02
 */
public class AlarmListInfo {
    private String segment;
    private String segmentName;
    private String workshopCode;
    private String workshopName;
    private String workareaCode;
    private String workareaName;
    private String stationCode;
    private String stationName;
    private AlarmLevel alarmLevel;
    private int alarmType;
    private String deviceName;

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
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

    public String getWorkshopName() {
        return workshopName;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }

    public String getWorkareaCode() {
        return workareaCode;
    }

    public void setWorkareaCode(String workareaCode) {
        this.workareaCode = workareaCode;
    }

    public String getWorkareaName() {
        return workareaName;
    }

    public void setWorkareaName(String workareaName) {
        this.workareaName = workareaName;
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

    public AlarmLevel getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(AlarmLevel alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public int getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(int alarmType) {
        this.alarmType = alarmType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
