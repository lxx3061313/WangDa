package com.wangda.alarm.service.common.util.cache.redisclient.support;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import com.lambdaworks.redis.RedisFuture;
import com.lambdaworks.redis.ScriptOutputType;
import com.lambdaworks.redis.api.async.RedisAsyncCommands;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhenwei.liu
 * @since 2017-01-17
 */
public class LettuceSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(LettuceSupport.class);

    /**
     * 使用指定 function 聚合多个 future
     *
     * @param aggregationFunction 聚合函数
     * @param inputs 需要聚合的 future list
     * @param <Input> future 返回值
     * @param <Output> 聚合函数返回值
     * @return 聚合结果的 future
     * @see com.google.common.util.concurrent.Futures#successfulAsList(Iterable)
     */
    public static <Input, Output> CompletionStage<Output> combine(
            List<CompletionStage<Input>> inputs,
            AggregationFunction<Input, Output> aggregationFunction) {
        return combine(inputs, aggregationFunction, MoreExecutors.directExecutor());
    }

    /**
     * 使用指定 function 聚合多个 future
     *
     * @param inputs 需要聚合的 future list
     * @param aggregationFunction 聚合函数
     * @param executor 异步执行的 executor
     * @param <Input> future 返回值
     * @param <Output> 聚合函数返回值
     * @return 聚合结果的 future
     * @see com.google.common.util.concurrent.Futures#successfulAsList(Iterable)
     */
    public static <Input, Output> CompletionStage<Output> combine(
            List<CompletionStage<Input>> inputs,
            AggregationFunction<Input, Output> aggregationFunction,
            Executor executor) {

        Preconditions
                .checkArgument(inputs != null && inputs.size() > 0, "inputs must not be empty");

        ErrorAsNullBiFunction<Input> nullFunc = new ErrorAsNullBiFunction<>();
        List<Input> chain = new ArrayList<>(inputs.size());
        initListWithNull(chain, inputs.size());
        CompletionStage<Input> previous = null;

        for (int i = 0; i < inputs.size(); i++) {
            final int idx = i;
            CompletionStage<Input> trans = inputs.get(i).handleAsync(nullFunc, executor);
            if (previous != null) {
                previous = previous.thenCombineAsync(trans, (prev, current) -> {
                    chain.set(idx, current);
                    return current;
                }, executor);
            } else {
                previous = trans.thenApplyAsync(input -> {
                    chain.set(0, input);
                    return input;
                }, executor);
            }
        }

        return previous.thenApplyAsync(input -> aggregationFunction.apply(chain), executor);
    }

    /**
     * 阻塞的获取 future 值
     *
     * @param stage future
     * @param <T> future 返回值类型
     * @return future 返回值
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(CompletionStage<T> stage)
            throws ExecutionException, InterruptedException {
        if (stage instanceof Future) {
            return (T) ((Future) stage).get();
        } else {
            throw new UnsupportedOperationException(
                    String.format("operation is not supported by %s", stage.getClass()));
        }
    }

    private static final ConcurrentMap<String, String> SCRIPT_DIGEST_CACHE = Maps
            .newConcurrentMap();

    private static final String[] EMPTY_ARGS = new String[0];

    public static <T> CompletionStage<T> evalAutoSha(RedisAsyncCommands<String, String> client,
            String script, ScriptOutputType outputType) {
        return evalAutoSha(client, script, outputType, EMPTY_ARGS);
    }

    public static <T> CompletionStage<T> evalAutoSha(RedisAsyncCommands<String, String> client,
            String script, ScriptOutputType outputType, String[] keys) {
        return evalAutoSha(client, script, outputType, keys, EMPTY_ARGS);
    }

    /**
     * <pre>
     * eval 作为脚本命令, 对 cluster node 是敏感的.
     * lettuce 处理 cluster 命令的方式是用命令 args 做 hash, 通过 hash 确定执行命令的节点
     * 1. 命令参数生成方式参见 {@code RedisCommandBuilder}, 不同命令参数不一样
     * 2. 命令 hash 生成方式参见 {@code SlotHash.getSlot()}
     *
     * 对于 eval 来说, 默认的 args 包括 script + keys.length + keys,
     * 因此如果脚本入参同, 则执行节点不同
     * </pre>
     */
    public static <T> CompletionStage<T> evalAutoSha(RedisAsyncCommands<String, String> client,
            String script, ScriptOutputType outputType, String[] keys, String[] vals) {
        String digest = SCRIPT_DIGEST_CACHE.get(script);
        CompletableFuture<T> future = new CompletableFuture<>();

        if (digest == null) {
            digest = loadScriptDigestFromLocal(client, script);
            SCRIPT_DIGEST_CACHE.putIfAbsent(script, digest);
        }

        RedisFuture<T> evalsha = client.evalsha(digest, outputType, keys, vals);
        evalsha.thenAccept(future::complete)
                .exceptionally(t1 -> {
                    LOGGER.error("evalsha1 error", t1);
                    client.scriptLoad(script)
                            .thenAccept(digest1 -> {
                                RedisFuture<T> evalsha1 = client.evalsha(digest1, outputType, keys, vals);
                                evalsha1.thenAccept(future::complete)
                                                .exceptionally(t2 -> {
                                                    LOGGER.error("evalsha2 error", t2);
                                                    future.completeExceptionally(t2);
                                                    return null;
                                                });
                                    }
                            ).exceptionally(t3 -> {
                        LOGGER.error("loadscript error", t3);
                        future.completeExceptionally(t3);
                        return null;
                    });
                    return null;
                });

        return future;
    }

    public static <T> ListenableFuture<T> adpaptToListenableFuture(
            CompletionStage<T> completionStage) {
        SettableFuture<T> future = SettableFuture.create();
        completionStage.thenAccept(future::set)
                .exceptionally(t -> {
                    future.setException(t);
                    return null;
                });
        return future;
    }

    private static <T> CompletionStage<Void> evalDigestAndSetVal(
            RedisAsyncCommands<String, String> client,
            String digest, ScriptOutputType outputType, String[] keys, String[] vals,
            Executor executor, CompletableFuture<T> future) {
        RedisFuture<T> evalSha = client.evalsha(digest, outputType, keys, vals);
        return evalSha.thenAcceptAsync(future::complete, executor);
    }

    private static RedisFuture<String> loadScriptDegistFromRedis(
            RedisAsyncCommands<String, String> client, String script) {
        return client.scriptLoad(script);
    }

    private static String loadScriptDigestFromLocal(RedisAsyncCommands<String, String> client,
            String script) {
        return client.digest(script);
    }

    private static <T> void initListWithNull(List<T> list, int length) {
        for (int i = 0; i < length; i++) {
            list.add(null);
        }
    }
}
