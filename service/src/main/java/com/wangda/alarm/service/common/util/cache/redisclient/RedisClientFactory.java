package com.wangda.alarm.service.common.util.cache.redisclient;

import com.lambdaworks.redis.api.async.RedisAsyncCommands;
import com.wangda.alarm.service.common.springconfig.MValue;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-02-22
 */
@Service
public class RedisClientFactory {
    private final static Logger logger = LoggerFactory.getLogger(RedisClientFactory.class);

    @Value("${main.redis.host}")
    private String redisHost;

    @Value("${main.redis.port}")
    private String redisPort="6379";

    @Value("${main.redis.password}")
    private String redisPass;

    private RedisAsyncCommands<String, String> CLIENT;

    @PostConstruct
    private void initClient() {
        CLIENT= RedisClientBuilder
                .createSingle(redisHost, Integer.parseInt(redisPort))
                .setTimeout(1000)
                .setTimeoutUnit(TimeUnit.MILLISECONDS)
                .setPassword(redisPass)
                .buildAsync();
    }

    public RedisAsyncCommands<String, String> getCLIENT() {
        return CLIENT;
    }
}
