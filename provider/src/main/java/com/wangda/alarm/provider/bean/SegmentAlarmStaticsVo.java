package com.wangda.alarm.provider.bean;

import java.util.List;

/**
 * @author cyong
 * @version 2017-11-14
 */
public class SegmentAlarmStaticsVo {
    /**
     * 一级报警数
     */
    private long levelOneCount;

    /**
     * 二级报警数
     */
    private long levelTwoCount;

    /**
     * 三级报警数
     */
    private long levelThreeCount;

    /**
     * 预警数
     */
    private long warnCount;

    List<DeptAlarmItemVo> workshopStatics;

    public List<DeptAlarmItemVo> getWorkshopStatics() {
        return workshopStatics;
    }

    public void setWorkshopStatics(
            List<DeptAlarmItemVo> workshopStatics) {
        this.workshopStatics = workshopStatics;
    }

    public long getLevelOneCount() {
        return levelOneCount;
    }

    public void setLevelOneCount(long levelOneCount) {
        this.levelOneCount = levelOneCount;
    }

    public long getLevelTwoCount() {
        return levelTwoCount;
    }

    public void setLevelTwoCount(long levelTwoCount) {
        this.levelTwoCount = levelTwoCount;
    }

    public long getLevelThreeCount() {
        return levelThreeCount;
    }

    public void setLevelThreeCount(long levelThreeCount) {
        this.levelThreeCount = levelThreeCount;
    }

    public long getWarnCount() {
        return warnCount;
    }

    public void setWarnCount(long warnCount) {
        this.warnCount = warnCount;
    }
}
