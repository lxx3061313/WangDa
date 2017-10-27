package com.wangda.alarm.service.bean.vo;

/**
 * @author lixiaoxiong
 * @version 2017-10-26
 */
public class AlarmStatisticsVo {

    /**
     * 一级报警数
     */
    private int levelOneCount;

    /**
     * 二级报警数
     */
    private int leveTowCount;

    /**
     * 三级报警数
     */
    private int leveThreeCount;

    /**
     * 预警数
     */
    private int warnCount;

    /**
     * 达州电务段报警数
     */
    private int dzCount;

    /**
     * 成都电务段报警数
     */
    private int cdCount;

    /**
     * 重庆电务段报警数
     */
    private int cqCount;

    /**
     * 贵阳电务段报警数
     */
    private int gyCount;

    /**
     * 贵阳北电务段报警数
     */
    private int gybCount;

    public int getLevelOneCount() {
        return levelOneCount;
    }

    public void setLevelOneCount(int levelOneCount) {
        this.levelOneCount = levelOneCount;
    }

    public int getLeveTowCount() {
        return leveTowCount;
    }

    public void setLeveTowCount(int leveTowCount) {
        this.leveTowCount = leveTowCount;
    }

    public int getLeveThreeCount() {
        return leveThreeCount;
    }

    public void setLeveThreeCount(int leveThreeCount) {
        this.leveThreeCount = leveThreeCount;
    }

    public int getWarnCount() {
        return warnCount;
    }

    public void setWarnCount(int warnCount) {
        this.warnCount = warnCount;
    }

    public int getDzCount() {
        return dzCount;
    }

    public void setDzCount(int dzCount) {
        this.dzCount = dzCount;
    }

    public int getCdCount() {
        return cdCount;
    }

    public void setCdCount(int cdCount) {
        this.cdCount = cdCount;
    }

    public int getCqCount() {
        return cqCount;
    }

    public void setCqCount(int cqCount) {
        this.cqCount = cqCount;
    }

    public int getGyCount() {
        return gyCount;
    }

    public void setGyCount(int gyCount) {
        this.gyCount = gyCount;
    }

    public int getGybCount() {
        return gybCount;
    }

    public void setGybCount(int gybCount) {
        this.gybCount = gybCount;
    }
}
