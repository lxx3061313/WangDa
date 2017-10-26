package com.wangda.alarm.service.bean.vo.req;

import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmLevel;

/**
 * @author lixiaoxiong
 * @version 2017-10-26
 */
public class AlarmReq {

    /**
     * station是一个拼接变量
     */
    private String station;
    private AlarmLevel level;
    private String alarmType;

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
}
