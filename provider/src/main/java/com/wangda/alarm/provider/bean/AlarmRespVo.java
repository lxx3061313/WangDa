package com.wangda.alarm.provider.bean;

/**
 * @author lixiaoxiong
 * @version 2017-11-01
 */
public class AlarmRespVo {
    private int totalCount;
    private AlarmOutlineVo outline;
    private AlarmDetailVo details;

    public AlarmOutlineVo getOutline() {
        return outline;
    }

    public void setOutline(AlarmOutlineVo outline) {
        this.outline = outline;
    }

    public AlarmDetailVo getDetails() {
        return details;
    }

    public void setDetails(AlarmDetailVo details) {
        this.details = details;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
