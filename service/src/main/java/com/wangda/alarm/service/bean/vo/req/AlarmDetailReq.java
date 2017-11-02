package com.wangda.alarm.service.bean.vo.req;

import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmLevel;
import com.wangda.alarm.service.bean.standard.protocol.StandardAlarmType;

/**
 * @author lixiaoxiong
 * @version 2017-11-03
 */
public class AlarmDetailReq {
    private String segmentCode;
    private String workshopCode;
    private String workareaCode;
    private String stationCode;
    private StandardAlarmType alarmType;
    private AlarmLevel level;
    private String deviceName;
    private int pageSize;
    private int currentPage;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getSegmentCode() {
        return segmentCode;
    }

    public void setSegmentCode(String segmentCode) {
        this.segmentCode = segmentCode;
    }

    public String getWorkshopCode() {
        return workshopCode;
    }

    public void setWorkshopCode(String workshopCode) {
        this.workshopCode = workshopCode;
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

    public StandardAlarmType getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(StandardAlarmType alarmType) {
        this.alarmType = alarmType;
    }

    public AlarmLevel getLevel() {
        return level;
    }

    public void setLevel(AlarmLevel level) {
        this.level = level;
    }
}
