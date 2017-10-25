package com.wangda.alarm.service.common.util.cache.redisclient.support;

import java.util.List;
import java.util.function.Function;

/**
 * @author zhenwei.liu
 * @since 2017-01-17
 */
public interface AggregationFunction<Input, Output> extends Function<List<Input>, Output> {

}
