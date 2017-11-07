package com.wangda.alarm.provider.bean;

/**
 * @author lixiaoxiong
 * @version 2017-11-06
 */
public class ListItemVo {
    private String value;
    private String key;

    public ListItemVo(String value, String key) {
        this.value = value;
        this.key = key;
    }

    public ListItemVo() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ListItemVo)) {
            return false;
        }

        ListItemVo itemVo = (ListItemVo) o;

        if (getValue() != null ? !getValue().equals(itemVo.getValue())
                : itemVo.getValue() != null) {
            return false;
        }
        return getKey() != null ? getKey().equals(itemVo.getKey()) : itemVo.getKey() == null;

    }

    @Override
    public int hashCode() {
        int result = getValue() != null ? getValue().hashCode() : 0;
        result = 31 * result + (getKey() != null ? getKey().hashCode() : 0);
        return result;
    }
}
