package com.wangda.alarm.provider.bean;

/**
 * @author lixiaoxiong
 * @version 2017-11-01
 */
public class AlarmDetailVo {
    private String station;
    private String alarmLeve;
    private String alarmContext;
    private String alarmTime;
    private String recoverTime;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getAlarmLeve() {
        return alarmLeve;
    }

    public void setAlarmLeve(String alarmLeve) {
        this.alarmLeve = alarmLeve;
    }

    public String getAlarmContext() {
        return alarmContext;
    }

    public void setAlarmContext(String alarmContext) {
        this.alarmContext = alarmContext;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(String recoverTime) {
        this.recoverTime = recoverTime;
    }
}
