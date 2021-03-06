package com.wangda.alarm.service.common.util.pojo.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DataJsonV1<T> {

    public final boolean ret = true;
    public final T data;

    @JsonCreator
    public DataJsonV1(@JsonProperty("data") T data) {
        this.data = data;
    }
}
