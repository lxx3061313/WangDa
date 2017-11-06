package com.wangda.alarm.provider.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixiaoxiong
 * @version 2017-11-06
 */
public class WorkareaVo {
    private String value;
    private String key;
    List<ListItemVo> stations = new ArrayList<>();

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<ListItemVo> getStations() {
        return stations;
    }

    public void setStations(List<ListItemVo> stations) {
        this.stations = stations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkareaVo)) {
            return false;
        }

        WorkareaVo that = (WorkareaVo) o;

        if (getValue() != null ? !getValue().equals(that.getValue()) : that.getValue() != null) {
            return false;
        }
        return getKey() != null ? getKey().equals(that.getKey()) : that.getKey() == null;

    }

    @Override
    public int hashCode() {
        int result = getValue() != null ? getValue().hashCode() : 0;
        result = 31 * result + (getKey() != null ? getKey().hashCode() : 0);
        return result;
    }
}
