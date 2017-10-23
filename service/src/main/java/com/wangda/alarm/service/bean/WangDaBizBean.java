package com.wangda.alarm.service.bean;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
public class WangDaBizBean<T> {
    private long dataLength;
    private BizBeanType beanType;
    T Data;

    public long getDataLength() {
        return dataLength;
    }

    public void setDataLength(long dataLength) {
        this.dataLength = dataLength;
    }

    public BizBeanType getBeanType() {
        return beanType;
    }

    public void setBeanType(BizBeanType beanType) {
        this.beanType = beanType;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }
}
