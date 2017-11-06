package com.wangda.alarm.provider.bean;

/**
 * @author lixiaoxiong
 * @version 2017-11-06
 */
public class ListItemVo {
    private String value;
    private String code;

    public ListItemVo(String value, String code) {
        this.value = value;
        this.code = code;
    }

    public ListItemVo() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        return getCode() != null ? getCode().equals(itemVo.getCode()) : itemVo.getCode() == null;

    }

    @Override
    public int hashCode() {
        int result = getValue() != null ? getValue().hashCode() : 0;
        result = 31 * result + (getCode() != null ? getCode().hashCode() : 0);
        return result;
    }
}
