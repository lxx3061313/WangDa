package com.wangda.alarm.provider.bean;

/**
 * @author cyong
 * @version 2017-11-05
 */
public class OpLogItemVo {
    private String userName;
    private String logTime;
    private String logContext;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getLogContext() {
        return logContext;
    }

    public void setLogContext(String logContext) {
        this.logContext = logContext;
    }
}
