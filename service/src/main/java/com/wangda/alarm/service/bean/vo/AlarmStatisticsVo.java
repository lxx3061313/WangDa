package com.wangda.alarm.service.bean.vo;

/**
 * @author lixiaoxiong
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
    private long leveTowCount;

    /**
     * 三级报警数
     */
    private long leveThreeCount;

    /**
     * 预警数
     */
    private long warnCount;

    /**
     * 达州电务段报警数
     */
    private long dzCount;

    /**
     * 成都电务段报警数
     */
    private long cdCount;

    /**
     * 重庆电务段报警数
     */
    private long cqCount;

    /**
     * 贵阳电务段报警数
     */
    private long gyCount;

    /**
     * 贵阳北电务段报警数
     */
    private long gybCount;

    public long getLevelOneCount() {
        return levelOneCount;
    }

    public void setLevelOneCount(long levelOneCount) {
        this.levelOneCount = levelOneCount;
    }

    public long getLeveTowCount() {
        return leveTowCount;
    }

    public void setLeveTowCount(long leveTowCount) {
        this.leveTowCount = leveTowCount;
    }

    public long getLeveThreeCount() {
        return leveThreeCount;
    }

    public void setLeveThreeCount(long leveThreeCount) {
        this.leveThreeCount = leveThreeCount;
    }

    public long getWarnCount() {
        return warnCount;
    }

    public void setWarnCount(long warnCount) {
        this.warnCount = warnCount;
    }

    public long getDzCount() {
        return dzCount;
    }

    public void setDzCount(long dzCount) {
        this.dzCount = dzCount;
    }

    public long getCdCount() {
        return cdCount;
    }

    public void setCdCount(long cdCount) {
        this.cdCount = cdCount;
    }

    public long getCqCount() {
        return cqCount;
    }

    public void setCqCount(long cqCount) {
        this.cqCount = cqCount;
    }

    public long getGyCount() {
        return gyCount;
    }

    public void setGyCount(long gyCount) {
        this.gyCount = gyCount;
    }

    public long getGybCount() {
        return gybCount;
    }

    public void setGybCount(long gybCount) {
        this.gybCount = gybCount;
    }
}
