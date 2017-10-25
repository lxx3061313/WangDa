package com.wangda.alarm.service.common.util.cache.redisclient.support;

import java.util.function.BiFunction;

/**
 * @author zhenwei.liu
 * @since 2017-01-17
 */
class ErrorAsNullBiFunction<Input> implements BiFunction<Input, Throwable, Input> {

    @Override
    public Input apply(Input input, Throwable throwable) {
        if (throwable != null) {
            return null;
        }
        return input;
    }
}
