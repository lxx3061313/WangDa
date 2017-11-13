package com.wangda.alarm.provider.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixiaoxiong
 * @version 2017-11-05
 */
public class OpLogVo {
    private int totalCount;
    private List<OpLogItemVo> logs = new ArrayList<>();

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<OpLogItemVo> getLogs() {
        return logs;
    }

    public void setLogs(List<OpLogItemVo> logs) {
        this.logs = logs;
    }
}
