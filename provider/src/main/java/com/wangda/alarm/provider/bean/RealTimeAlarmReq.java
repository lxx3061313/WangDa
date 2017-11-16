package com.wangda.alarm.provider.bean;

/**
 * @author cyong
 * @version 2017-11-05
 */
public class RealTimeAlarmReq {
    private int currentPage;
    private int pageSize;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
