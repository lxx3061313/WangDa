package com.wangda.alarm.service.bean.vo;

import java.util.List;

/**
 * @author lixiaoxiong
 * @version 2017-10-27
 */
public class RealTimeAlarmVo {
    private int totalCount;
    private List<RealTimeAlarmItem> alarms;

    public List<RealTimeAlarmItem> getAlarms() {
        return alarms;
    }

    public void setAlarms(List<RealTimeAlarmItem> alarms) {
        this.alarms = alarms;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
