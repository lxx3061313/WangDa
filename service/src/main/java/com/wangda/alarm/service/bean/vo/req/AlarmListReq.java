package com.wangda.alarm.service.bean.vo.req;

import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmLevel;
import com.wangda.alarm.service.bean.standard.protocol.StandardAlarmType;
import java.util.Date;

/**
 * @author lixiaoxiong
 * @version 2017-10-26
 */
public class AlarmListReq {

    private String segmentCode;
    private String workshopCode;
    private String workareaCode;
    private String stationCode;

    /**
     * 报警级别
     */
    private AlarmLevel level;

    /**
     * 报警类型
     */
    private StandardAlarmType alarmType;

    private Date startTime;
    private Date endTime;

    private int pageSize = 10;

    private int currentPage = 0;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public AlarmLevel getLevel() {
        return level;
    }

    public void setLevel(AlarmLevel level) {
        this.level = level;
    }

    public StandardAlarmType getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(StandardAlarmType alarmType) {
        this.alarmType = alarmType;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getSegmentCode() {
        return segmentCode;
    }

    public void setSegmentCode(String segmentCode) {
        this.segmentCode = segmentCode;
    }

    public String getWorkshopCode() {
        return workshopCode;
    }

    public void setWorkshopCode(String workshopCode) {
        this.workshopCode = workshopCode;
    }

    public String getWorkareaCode() {
        return workareaCode;
    }

    public void setWorkareaCode(String workareaCode) {
        this.workareaCode = workareaCode;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }
}
