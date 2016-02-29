package com.zhuimengfb.core.framework.interceptor;

/**
 * Created by bo on 2016/2/28.
 */

import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LogInterceptor
        extends HandlerInterceptorAdapter {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String requestURL = request.getRequestURL().toString();
        if (requestURL != null) {
            String requestURLTempString = requestURL.substring(requestURL.indexOf(request.getContextPath()) + request.getContextPath().length());
            if (requestURLTempString.indexOf("/static/") < 0) {
                System.out.println(request.getRemoteAddr());
                System.out.println(request.getRemotePort());

                System.out.println(request.getRequestedSessionId());
                System.out.println(requestURLTempString);

                Enumeration paramNames = request.getParameterNames();

                Map<String, Object> params = new TreeMap();
                while ((paramNames != null) && (paramNames.hasMoreElements())) {
                    String paramName = (String) paramNames.nextElement();
                    String[] values = request.getParameterValues(paramName);
                    String valuesString = "";
                    for (String value : values) {
                        valuesString = valuesString + value;
                    }
                    params.put(paramName, valuesString);
                }
                System.out.println("params = " + params.toString());
            }
        }
        return true;
    }
}
