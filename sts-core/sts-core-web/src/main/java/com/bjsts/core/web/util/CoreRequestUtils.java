package com.bjsts.core.web.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * author: sunshow.
 */
public class CoreRequestUtils {
    private static String[] httpProxyHeaderName = new String[] {
            "X-FORWARDED-FOR",
            "CDN-SRC-IP",
            "HTTP_CDN_SRC_IP",
            "X-Real-IP",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR",
    };

    public static String getRemoteAddr(HttpServletRequest request) {
        String[] clientIPArray = getClientIPArray(request);
        if (clientIPArray == null) {
            return null;
        }
        return clientIPArray[0];
    }

    public static String getClientIP(HttpServletRequest request) {
        for (String headerName : httpProxyHeaderName) {
            String clientIP = request.getHeader(headerName);
            if (StringUtils.isNotEmpty(clientIP)) {
                return clientIP;
            }
        }
        return request.getRemoteAddr();
    }

    public static String[] getClientIPArray(HttpServletRequest request) {
        String clientIP = getClientIP(request);
        if (StringUtils.isEmpty(clientIP)) {
            return null;
        }
        return StringUtils.split(clientIP, ",");
    }
}
