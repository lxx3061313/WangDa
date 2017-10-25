package com.wangda.alarm.service.common.util.cache.redisclient.support;

import java.util.List;

/**
 * @author zhenwei.liu
 * @since 2017-01-18
 */
public class Namespace {

    private String name;
    private String masterId;
    private List<Server> sentinelServerList; // host -> ports

    public String getName() {
        return name;
    }

    public Namespace setName(String name) {
        this.name = name;
        return this;
    }

    public String getMasterId() {
        return masterId;
    }

    public Namespace setMasterId(String masterId) {
        this.masterId = masterId;
        return this;
    }

    public List<Server> getSentinelServerList() {
        return sentinelServerList;
    }

    public Namespace setSentinelServerList(
            List<Server> sentinelServerList) {
        this.sentinelServerList = sentinelServerList;
        return this;
    }
}
