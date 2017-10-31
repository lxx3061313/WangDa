package com.wangda.alarm.service.dao.po;

import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmLevel;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmStatus;
import com.wangda.alarm.service.bean.standard.OverhaulType;
import java.util.Date;

/**
 * @author lixiaoxiong
 * @version 2017-10-25
 */
public class AlarmInfoPo {
    private int id;

    /**
     * 电务段code
     */
    private String segment;

    /**
     * 工作间code
     */
    private String workshopCode;

    /**
     * 工区code
     */
    private String workAreaCode;

    /**
     * 目的电报码(服务器编码)
     */
    private String targetTeleCode;

    /**
     * 源电报码(站机电报码)
     */
    private String sourceTeleCode;

    /**
     * 报警类型
     */
    private int alarmType;

    /**
     * 报警级别
     */
    private AlarmLevel alarmLevel;

    /**
     * 设备类型
     */
    private int deviceType;

    /**
     * 设备序号
     */
    private int deviceNo;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 报警时间
     */
    private Date alarmTime;

    /**
     * 恢复时间
     */
    private Date recoverTime;

    /**
     * 报警状态
     */
    private AlarmStatus status;

    /**
     * 报警内容
     */
    private String alarmContext;

    /**
     * 检修或天窗标记
     */
    private OverhaulType overhaulFlag;


    private Date createTime;
    private String remark;

    private AlarmExtInfoPo extInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTargetTeleCode() {
        return targetTeleCode;
    }

    public void setTargetTeleCode(String targetTeleCode) {
        this.targetTeleCode = targetTeleCode;
    }

    public String getSourceTeleCode() {
        return sourceTeleCode;
    }

    public void setSourceTeleCode(String sourceTeleCode) {
        this.sourceTeleCode = sourceTeleCode;
    }

    public int getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(int alarmType) {
        this.alarmType = alarmType;
    }

    public AlarmLevel getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(AlarmLevel alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(int deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    public Date getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(Date recoverTime) {
        this.recoverTime = recoverTime;
    }

    public AlarmStatus getStatus() {
        return status;
    }

    public void setStatus(AlarmStatus status) {
        this.status = status;
    }

    public String getAlarmContext() {
        return alarmContext;
    }

    public void setAlarmContext(String alarmContext) {
        this.alarmContext = alarmContext;
    }

    public OverhaulType getOverhaulFlag() {
        return overhaulFlag;
    }

    public void setOverhaulFlag(OverhaulType overhaulFlag) {
        this.overhaulFlag = overhaulFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public AlarmExtInfoPo getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(AlarmExtInfoPo extInfo) {
        this.extInfo = extInfo;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getWorkshopCode() {
        return workshopCode;
    }

    public void setWorkshopCode(String workshopCode) {
        this.workshopCode = workshopCode;
    }

    public String getWorkAreaCode() {
        return workAreaCode;
    }

    public void setWorkAreaCode(String workAreaCode) {
        this.workAreaCode = workAreaCode;
    }
}
