package com.wangda.alarm.service.common.util.cache.redisclient.support;

/**
 * @author zhenwei.liu
 * @since 2017-01-18
 */
public interface NamespaceManager {

    Namespace getNameSpace(String namespace);
}
