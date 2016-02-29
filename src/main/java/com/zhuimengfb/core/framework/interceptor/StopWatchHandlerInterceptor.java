package com.zhuimengfb.core.framework.interceptor;

/**
 * Created by bo on 2016/2/28.
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class StopWatchHandlerInterceptor
        extends HandlerInterceptorAdapter {
    private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal("StopWatch-StartTime");

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        long beginTime = System.currentTimeMillis();
        this.startTimeThreadLocal.set(Long.valueOf(beginTime));
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        long endTime = System.currentTimeMillis();
        long beginTime = ((Long) this.startTimeThreadLocal.get()).longValue();
        long consumeTime = endTime - beginTime;
        if (consumeTime > 50L) {
            System.out.println(String.format("%s --> %d millis", new Object[]{request.getRequestURI(), Long.valueOf(consumeTime)}));
        }
    }
}

