package com.wangda.alarm.provider.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cyong
 * @version 2017-11-06
 */
public class SegmentVo {
    private String key;
    private String value;
    private List<WorkshopVo> workshops = new ArrayList<>();

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

    public List<WorkshopVo> getWorkshops() {
        return workshops;
    }

    public void setWorkshops(List<WorkshopVo> workshops) {
        this.workshops = workshops;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SegmentVo)) {
            return false;
        }

        SegmentVo segmentVo = (SegmentVo) o;

        if (getKey() != null ? !getKey().equals(segmentVo.getKey()) : segmentVo.getKey() != null) {
            return false;
        }
        return getValue() != null ? getValue().equals(segmentVo.getValue())
                : segmentVo.getValue() == null;

    }

    @Override
    public int hashCode() {
        int result = getKey() != null ? getKey().hashCode() : 0;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }
}
