package com.wangda.alarm.service.common.util.cache;

public interface CachedStreamEntity {
    CachedStream getCachedStream();
    void flushStream();
}
