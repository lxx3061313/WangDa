package com.wangda.alarm.provider.bean;

import java.util.List;

/**
 * @author lixiaoxiong
 * @version 2017-11-06
 */
public class SelectItemValuesVo {
    private List<ListItemVo> alarmLevels;
    private List<ListItemVo> alarmTypes;
    private List<SectionVo> sections;

    public List<ListItemVo> getAlarmLevels() {
        return alarmLevels;
    }

    public void setAlarmLevels(List<ListItemVo> alarmLevels) {
        this.alarmLevels = alarmLevels;
    }

    public List<ListItemVo> getAlarmTypes() {
        return alarmTypes;
    }

    public void setAlarmTypes(List<ListItemVo> alarmTypes) {
        this.alarmTypes = alarmTypes;
    }

    public List<SectionVo> getSections() {
        return sections;
    }

    public void setSections(List<SectionVo> sections) {
        this.sections = sections;
    }
}
