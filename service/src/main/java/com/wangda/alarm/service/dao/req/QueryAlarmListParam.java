package com.wangda.alarm.service.dao.req;

import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmLevel;

/**
 * @author lixiaoxiong
 * @version 2017-11-01
 */
public class QueryAlarmListParam {
    private String segmentCode;
    private String workShopCode;
    private String workAreaCode;
    private String stationCode;
    private AlarmLevel level;
    private int alarmType;

    public String getSegmentCode() {
        return segmentCode;
    }

    public void setSegmentCode(String segmentCode) {
        this.segmentCode = segmentCode;
    }

    public String getWorkShopCode() {
        return workShopCode;
    }

    public void setWorkShopCode(String workShopCode) {
        this.workShopCode = workShopCode;
    }

    public String getWorkAreaCode() {
        return workAreaCode;
    }

    public void setWorkAreaCode(String workAreaCode) {
        this.workAreaCode = workAreaCode;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public AlarmLevel getLevel() {
        return level;
    }

    public void setLevel(AlarmLevel level) {
        this.level = level;
    }

    public int getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(int alarmType) {
        this.alarmType = alarmType;
    }
}
