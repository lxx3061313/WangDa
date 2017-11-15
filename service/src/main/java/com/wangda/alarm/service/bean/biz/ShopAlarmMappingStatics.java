package com.wangda.alarm.service.bean.biz;

import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmLevel;
import java.util.Map;

/**
 * @author lixiaoxiong
 * @version 2017-11-15
 */
public class ShopAlarmMappingStatics {
    private String segmentCode;

    /**
     * key:level value:alarmCount
     */
    Map<AlarmLevel, Integer> alarmLevelStatics;

    /**
     * key:workshotCode, value: alarmCount
     */
    Map<String, Integer> alarmShopStatics;

    /**
     * key:dept simple name value:deptInfo
     */
    Map<String, DeptInfo> deptInfoMap;

    public Map<AlarmLevel, Integer> getAlarmLevelStatics() {
        return alarmLevelStatics;
    }

    public void setAlarmLevelStatics(
            Map<AlarmLevel, Integer> alarmLevelStatics) {
        this.alarmLevelStatics = alarmLevelStatics;
    }

    public Map<String, Integer> getAlarmShopStatics() {
        return alarmShopStatics;
    }

    public void setAlarmShopStatics(Map<String, Integer> alarmShopStatics) {
        this.alarmShopStatics = alarmShopStatics;
    }

    public Map<String, DeptInfo> getDeptInfoMap() {
        return deptInfoMap;
    }

    public void setDeptInfoMap(
            Map<String, DeptInfo> deptInfoMap) {
        this.deptInfoMap = deptInfoMap;
    }

    public String getSegmentCode() {
        return segmentCode;
    }

    public void setSegmentCode(String segmentCode) {
        this.segmentCode = segmentCode;
    }
}
