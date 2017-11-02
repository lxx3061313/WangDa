package com.wangda.alarm.service.bean.vo.req;

import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmLevel;

/**
 * @author lixiaoxiong
 * @version 2017-10-26
 */
public class AlarmReq {

    /**
     * station是一个拼接变量,
     * 段#车间#工区#车站
     */
    private String station;

    /**
     * 报警级别
     */
    private AlarmLevel level;

    /**
     * 报警类型
     */
    private String alarmType;

    private int pageSize = 10;

    private int currentSize = 10;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public AlarmLevel getLevel() {
        return level;
    }

    public void setLevel(AlarmLevel level) {
        this.level = level;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }
}
