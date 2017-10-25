package com.wangda.alarm.service.impl.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author yushen.ma
 * @version 2016-07-05
 */
abstract class UFilter<T> extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        if (excludeUri(request, response)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            try {
                T user = this.getLoginInfo(request);
                if (user == null) {
                    this.onUserNotLogin(request, response);
                    return;
                }
                this.onUserLogin(user);
            } catch (Exception e) {
                try {
                    this.onCheckException(e, request, response);
                    return;
                } catch (Throwable ignore) {
                }
            }
            // 这里抛出异常不是本类的问题
            filterChain.doFilter(request, response);
        } finally {
            this.onFinal();
        }
    }

    protected boolean excludeUri(HttpServletRequest request, HttpServletResponse response) {
        return false;
    }

    private T getLoginInfo(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getCookies() == null) {
            return null;
        }
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if (cookie.getName().equals(cookieName())) {
                return this.authUser(cookie.getValue(), httpServletRequest);
            }
        }
        return null;
    }

    /**
     * 远程载入用户信息 如果返回为null, 认为用户没有登录成功
     */
    protected abstract T authUser(String token, HttpServletRequest httpServletRequest);

    protected abstract String cookieName();

    /**
     * 用户登录信息验证成功
     */
    protected abstract void onUserLogin(T user);

    /**
     * 用户登录信息验证失败
     */
    protected abstract void onUserNotLogin(HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 请求结束后
     */
    protected abstract void onFinal();

    /**
     * 远程调用用户中心认证失败, 默认处理成用户没登录
     */
    protected void onCheckException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        logger.error("调用用户中中心验证登录信息失败", e);
        try {
            this.onUserNotLogin(request, response);
        } catch (Throwable ignore) {
        } // 客户端关了
    }

    @Override
    public void destroy() {
    }

}
