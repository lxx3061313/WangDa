package com.wangda.alarm.provider.bean;

import java.util.List;

/**
 * @author zhangxin
 * @version 2017-11-14
 */
public class AlarmDetailResp {
    private int totalCount;
    private List<AlarmDetailVo> details;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<AlarmDetailVo> getDetails() {
        return details;
    }

    public void setDetails(List<AlarmDetailVo> details) {
        this.details = details;
    }
}
