package com.wangda.alarm.service.bean.standard.alarminfo.fault;

import java.util.List;

/**
 * @author lixiaoxiong
 * @version 2017-10-24
 */
public class FaultBody {
    /** 实时数据标记 */
    private byte realTimeData;

    /** 记录数 记录只会有1个*/
    private short recordNum;

    List<FaultRecord> records;

    public byte getRealTimeData() {
        return realTimeData;
    }

    public void setRealTimeData(byte realTimeData) {
        this.realTimeData = realTimeData;
    }

    public short getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(short recordNum) {
        this.recordNum = recordNum;
    }

    public List<FaultRecord> getRecords() {
        return records;
    }

    public void setRecords(List<FaultRecord> records) {
        this.records = records;
    }
}
