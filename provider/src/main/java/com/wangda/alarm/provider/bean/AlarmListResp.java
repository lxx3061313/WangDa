package com.wangda.alarm.provider.bean;

import java.util.List;

/**
 * @author lixiaoxiong
 * @version 2017-11-03
 */
public class AlarmListResp {
    private int totalCount;
    private List<AlarmOutlineVo> alarms;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<AlarmOutlineVo> getAlarms() {
        return alarms;
    }

    public void setAlarms(List<AlarmOutlineVo> alarms) {
        this.alarms = alarms;
    }
}
