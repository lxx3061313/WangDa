package com.wangda.alarm.service.bean;

import java.util.List;

/**
 * @author lixiaoxiong
 * @version 2017-10-24
 */
public class FaultBody {
    /** 实时数据标记 */
    private byte realTimeData;

    /** 记录数 todo 记录可能>1个吗*/
    private int recordNum;

    List<FaultRecord> records;

    public byte getRealTimeData() {
        return realTimeData;
    }

    public void setRealTimeData(byte realTimeData) {
        this.realTimeData = realTimeData;
    }

    public int getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(int recordNum) {
        this.recordNum = recordNum;
    }

    public List<FaultRecord> getRecords() {
        return records;
    }

    public void setRecords(List<FaultRecord> records) {
        this.records = records;
    }
}
