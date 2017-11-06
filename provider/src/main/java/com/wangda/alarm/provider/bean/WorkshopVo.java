package com.wangda.alarm.provider.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixiaoxiong
 * @version 2017-11-06
 */
public class WorkshopVo {
    private String key;
    private String value;
    private List<WorkareaVo> areas = new ArrayList<>();

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

    public List<WorkareaVo> getAreas() {
        return areas;
    }

    public void setAreas(List<WorkareaVo> areas) {
        this.areas = areas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkshopVo)) {
            return false;
        }

        WorkshopVo that = (WorkshopVo) o;

        if (getKey() != null ? !getKey().equals(that.getKey()) : that.getKey() != null) {
            return false;
        }
        return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;

    }

    @Override
    public int hashCode() {
        int result = getKey() != null ? getKey().hashCode() : 0;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }
}
