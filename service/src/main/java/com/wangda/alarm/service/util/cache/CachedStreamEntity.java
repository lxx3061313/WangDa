package com.wangda.alarm.service.util.cache;

public interface CachedStreamEntity {
    CachedStream getCachedStream();
    void flushStream();
}
