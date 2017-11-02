package com.wangda.alarm.service.dao.po;

import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmLevel;

/**
 * @author lixiaoxiong
 * @version 2017-11-02
 */
public class AlarmListPo {
    private String sourceTelecode;
    private AlarmLevel alarmLevel;
    private int alarmType;
    private String deviceName;
    public String getSourceTelecode() {
        return sourceTelecode;
    }

    public void setSourceTelecode(String sourceTelecode) {
        this.sourceTelecode = sourceTelecode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public AlarmLevel getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(AlarmLevel alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public void setAlarmType(int alarmType) {
        this.alarmType = alarmType;
    }

    public int getAlarmType() {
        return alarmType;
    }
}
