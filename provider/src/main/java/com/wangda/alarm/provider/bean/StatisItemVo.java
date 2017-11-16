package com.wangda.alarm.provider.bean;

/**
 * @author cyong
 * @version 2017-11-13
 */
public class StatisItemVo {
    private String segmentCode;
    private String segmentName;
    private int alarmCount;

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

    public int getAlarmCount() {
        return alarmCount;
    }

    public void setAlarmCount(int alarmCount) {
        this.alarmCount = alarmCount;
    }
}
