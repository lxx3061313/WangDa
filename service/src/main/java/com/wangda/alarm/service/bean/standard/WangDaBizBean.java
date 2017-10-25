package com.wangda.alarm.service.bean.standard;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
public class WangDaBizBean<T> {
    private int dataLength;
    private BizBeanType beanType;
    T Data;

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
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
