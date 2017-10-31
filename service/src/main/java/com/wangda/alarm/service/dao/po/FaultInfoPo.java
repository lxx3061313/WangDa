package com.wangda.alarm.service.dao.po;

import java.util.Date;

/**
 * @author lixiaoxiong
 * @version 2017-10-31
 */
public class FaultInfoPo {
    private int id;
    private String targetTelecode;
    private String sourceTelecode;
    private Date nocTime;
    private Date processtime;
    private Date recoverTime;
    private Date reserveTime;
    private String faultReason;
    private Date creatTime;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getNocTime() {
        return nocTime;
    }

    public void setNocTime(Date nocTime) {
        this.nocTime = nocTime;
    }

    public Date getProcesstime() {
        return processtime;
    }

    public void setProcesstime(Date processtime) {
        this.processtime = processtime;
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

    public String getFaultReason() {
        return faultReason;
    }

    public void setFaultReason(String faultReason) {
        this.faultReason = faultReason;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getTargetTelecode() {
        return targetTelecode;
    }

    public void setTargetTelecode(String targetTelecode) {
        this.targetTelecode = targetTelecode;
    }

    public String getSourceTelecode() {
        return sourceTelecode;
    }

    public void setSourceTelecode(String sourceTelecode) {
        this.sourceTelecode = sourceTelecode;
    }
}