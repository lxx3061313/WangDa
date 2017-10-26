package com.wangda.alarm.service.bean.vo;

import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmLevel;

/**
 * @author lixiaoxiong
 * @version 2017-10-26
 */
public class AlarmInfoVo {

    /**
     * 段名称
     */
    private String segment;

    /**
     * 车间名称
     */
    private String workshop;

    /**
     * 工区名称
     */
    private String workarea;

    /**
     * 车站名称
     */
    private String station;

    /**
     * 报警级别
     */
    private AlarmLevel alarmLevel;

    /**
     * 报警内容
     */
    private String alarmContext;

    /**
     * 报警次数
     */
    private int repeatCount;

    /**
     * 报警时间
     */
    private String alarmTime;

    /**
     * 恢复时间
     */
    private String recoverTime;

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }

    public String getWorkarea() {
        return workarea;
    }

    public void setWorkarea(String workarea) {
        this.workarea = workarea;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public AlarmLevel getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(AlarmLevel alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getAlarmContext() {
        return alarmContext;
    }

    public void setAlarmContext(String alarmContext) {
        this.alarmContext = alarmContext;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
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
