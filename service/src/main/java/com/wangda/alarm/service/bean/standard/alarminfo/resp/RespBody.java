package com.wangda.alarm.service.bean.standard.alarminfo.resp;

import java.util.List;

/**
 * @author lixiaoxiong
 * @version 2017-10-24
 */
public class RespBody {
    /** 数据命名-1字节 */
    private byte dataName;

    /** 记录数-2字节 */
    private short recordNum;

    private List<RespRecord> respRecords;

    public byte getDataName() {
        return dataName;
    }

    public void setDataName(byte dataName) {
        this.dataName = dataName;
    }

    public short getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(short recordNum) {
        this.recordNum = recordNum;
    }

    public List<RespRecord> getRespRecords() {
        return respRecords;
    }

    public void setRespRecords(List<RespRecord> respRecords) {
        this.respRecords = respRecords;
    }
}
