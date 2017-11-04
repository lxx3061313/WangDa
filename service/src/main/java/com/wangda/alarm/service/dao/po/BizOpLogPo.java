package com.wangda.alarm.service.dao.po;

import java.util.Date;

/**
 * @author lixiaoxiong
 * @version 2017-09-15
 */
public class BizOpLogPo {

    /** 数据库自增id */
    private int id;

    /** 业务相关id */
    private String bizId;

    /**操作人/事件主体*/
    private String operator;

    /** 业务操作描述 */
    private String opDesc;

    /** 日志生成时间 */
    private Date createTime;

    /** 标签,指定一种日志类型 */
    private String opLabel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOpDesc() {
        return opDesc;
    }

    public void setOpDesc(String opDesc) {
        this.opDesc = opDesc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOpLabel() {
        return opLabel;
    }

    public void setOpLabel(String opLabel) {
        this.opLabel = opLabel;
    }
}
