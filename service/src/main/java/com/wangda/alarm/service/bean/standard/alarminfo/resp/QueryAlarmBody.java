package com.wangda.alarm.service.bean.standard.alarminfo.resp;

import java.util.Date;
import java.util.List;

/**
 * @author lixiaoxiong
 * @version 2017-10-25
 */
public class QueryAlarmBody {
    /**
     * 数据命名
     */
    private byte dataname;

    /**
     * 开始时间
     */
    private Date starttime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 报警类型数
     */
    private short alarmTypeNum;

    public byte getDataname() {
        return dataname;
    }

    public void setDataname(byte dataname) {
        this.dataname = dataname;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public short getAlarmTypeNum() {
        return alarmTypeNum;
    }

    public void setAlarmTypeNum(short alarmTypeNum) {
        this.alarmTypeNum = alarmTypeNum;
    }
}
