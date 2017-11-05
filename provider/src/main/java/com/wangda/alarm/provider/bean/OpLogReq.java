package com.wangda.alarm.provider.bean;

/**
 * @author lixiaoxiong
 * @version 2017-11-05
 */
public class OpLogReq {
    private int pageSize;
    private int currentPage;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
