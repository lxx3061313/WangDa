package com.wangda.alarm.provider.bean;

import java.util.List;

/**
 * @author cyong
 * @version 2017-10-26
 */
public class AlarmStatisticsVo {

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


    List<StatisItemVo> segmentCount;

    public List<StatisItemVo> getSegmentCount() {
        return segmentCount;
    }

    public void setSegmentCount(List<StatisItemVo> segmentCount) {
        this.segmentCount = segmentCount;
    }

    public long getLevelOneCount() {
        return levelOneCount;
    }

    public void setLevelOneCount(long levelOneCount) {
        this.levelOneCount = levelOneCount;
    }


    public long getWarnCount() {
        return warnCount;
    }

    public void setWarnCount(long warnCount) {
        this.warnCount = warnCount;
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
}
