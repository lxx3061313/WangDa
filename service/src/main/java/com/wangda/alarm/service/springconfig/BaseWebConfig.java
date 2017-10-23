package com.wangda.alarm.service.springconfig;

import com.wangda.alarm.service.springconfig.handler.JsonBodyExceptionResolver;
import com.wangda.alarm.service.springconfig.handler.JsonBodyMethodProcessor;
import com.wangda.alarm.service.springconfig.handler.MetricsExceptionResolver;
import com.wangda.alarm.service.springconfig.resovler.ShortDateResolver;
import com.wangda.alarm.service.springconfig.resovler.ShortTimeResolver;
import java.lang.reflect.Method;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.handler.MappedInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
public class BaseWebConfig extends DelegatingWebMvcConfiguration {
    @Bean
    public JsonBodyExceptionResolver jsonBodyExceptionResolver() {
        return new JsonBodyExceptionResolver();
    }

//    @Bean
//    public MappedInterceptor httpMetricsInterceptor() {
//        return new MappedInterceptor(null, new HttpMetricsInterceptor());
//    }

    @Bean
    public MetricsExceptionResolver metricsExceptionResolver() {
        return new MetricsExceptionResolver();
    }

    @Bean
    @Override
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {

        RequestMappingHandlerAdapter adapter = super.requestMappingHandlerAdapter();

        // FIXME: set JsonBodyMethodProcessor as the first
        List<HandlerMethodReturnValueHandler> returnValueHandlers = getDefault(adapter, "getDefaultReturnValueHandlers");
        returnValueHandlers.add(0, this.getMyMethodProcessor());
        adapter.setReturnValueHandlers(returnValueHandlers);

        return adapter;
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> getDefault(RequestMappingHandlerAdapter adapter, String methodName) {
        try {
            Method method = RequestMappingHandlerAdapter.class.getDeclaredMethod(methodName);
            method.setAccessible(true);
            return (List<T>) method.invoke(adapter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new ShortDateResolver());
        argumentResolvers.add(new ShortTimeResolver());
    }

    protected HandlerMethodReturnValueHandler getMyMethodProcessor() {
        return new JsonBodyMethodProcessor();
    }
}
