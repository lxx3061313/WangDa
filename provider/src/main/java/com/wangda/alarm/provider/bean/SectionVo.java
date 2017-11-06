package com.wangda.alarm.provider.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixiaoxiong
 * @version 2017-11-06
 */
public class SectionVo {
    private String key;
    private String value;
    private List<SegmentVo> segments = new ArrayList<>();

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<SegmentVo> getSegments() {
        return segments;
    }

    public void setSegments(List<SegmentVo> segments) {
        this.segments = segments;
    }
}
