package com.wangda.alarm.service.bean;

import java.util.Date;

/**
 * @author lixiaoxiong
 * @version 2017-10-24
 */
public class FaultRecord {

    /**
     * 通知时间
     */
    private Date nocTime;

    /**
     * 受理时间
     */
    private Date processTime;

    /**
     * 恢复时间
     */
    private Date recoverTime;

    /**
     * 预留时间
     */
    private Date reserveTime;

    /**
     * 故障原因长度
     */
    private int faultReasonLth;

    /**
     * 故障原因
     */
    private String faultReason;

    public Date getNocTime() {
        return nocTime;
    }

    public void setNocTime(Date nocTime) {
        this.nocTime = nocTime;
    }

    public Date getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Date processTime) {
        this.processTime = processTime;
    }

    public Date getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(Date recoverTime) {
        this.recoverTime = recoverTime;
    }

    public Date getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(Date reserveTime) {
        this.reserveTime = reserveTime;
    }

    public int getFaultReasonLth() {
        return faultReasonLth;
    }

    public void setFaultReasonLth(int faultReasonLth) {
        this.faultReasonLth = faultReasonLth;
    }

    public String getFaultReason() {
        return faultReason;
    }

    public void setFaultReason(String faultReason) {
        this.faultReason = faultReason;
    }
}
