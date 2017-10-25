package com.wangda.alarm.service.common.util.cache.redisclient;

import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.RedisURI.Builder;

/**
 * @author zhenwei.liu
 * @since 2017-01-17
 */
public class SingleServerClientBuilder extends RedisClientBuilder {

    private String host;
    private int port;

    public SingleServerClientBuilder(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    protected Builder redisURIBuilder() {
        return RedisURI.builder().withHost(host).withPort(port);
    }

    public SingleServerClientBuilder setHost(String host) {
        this.host = host;
        return this;
    }

    public SingleServerClientBuilder setPort(int port) {
        this.port = port;
        return this;
    }
}
