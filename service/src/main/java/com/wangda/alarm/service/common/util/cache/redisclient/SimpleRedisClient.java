package com.wangda.alarm.service.common.util.cache.redisclient;

import com.google.common.base.Strings;
import com.lambdaworks.redis.RedisFuture;
import com.wangda.alarm.service.common.util.cache.redisclient.RedisClientFactory;
import com.wangda.alarm.service.common.util.json.JsonUtil;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-26
 */
@Service
public class SimpleRedisClient {
    private final static Logger logger  = LoggerFactory.getLogger(SimpleRedisClient.class);
    @Resource
    RedisClientFactory redisClientFactory;

    public void set(String key, Object value) {
        redisClientFactory.getCLIENT().set(key, JsonUtil.of(value));
    }

    public Object get(String key, Class clz) {
        String s = get(key);
        if (!Strings.isNullOrEmpty(s)) {
            return JsonUtil.of(s, clz);
        }
        return null;
    }

    public String get(String key) {
        RedisFuture<String> redisFuture = redisClientFactory.getCLIENT().get(key);
        try {
           return redisFuture.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("redis读取被中断");
        } catch (ExecutionException e) {
            logger.error("redis操作执行错误");
        } catch (TimeoutException e) {
            logger.error("redis读取超时");
        }
        return null;
    }
}
