package com.jarvis.framework.webmvc.util;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class WebUtil {

    public static final String REMOTE_ADDR_SESSION_KEY = "REMOTE_ADDR_SESSION_KEY";

    public static HttpServletRequest request() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
        return servletRequestAttributes.getRequest();
    }

    public static HttpSession session() {
        return request().getSession(false);
    }

    public static String getParameter(String name) {
        return request().getParameter(name);
    }

    public static String getHeader(String name) {
        return request().getHeader(name);
    }

    public static String getTenantId() {
        String tenantId = getHeader("X-Tenant-Id");
        return null == tenantId ? "1" : tenantId;
    }

    public static String getRemoteAddr() {
        String ip = null;
        HttpSession session = session();
        if (null != session) {
            ip = (String)session.getAttribute("REMOTE_ADDR_SESSION_KEY");
        }

        if (null != ip) {
            return ip;
        } else {
            HttpServletRequest request = request();
            String[] headers = new String[]{"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
            String[] var4 = headers;
            int var5 = headers.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String header = var4[var6];
                ip = request.getHeader(header);
                if (!isUnknown(ip)) {
                    ip = getMultistageReverseProxyIp(ip);
                    break;
                }
            }

            if (null == ip) {
                ip = getMultistageReverseProxyIp(request.getRemoteAddr());
            }

            if (null != session) {
                session.setAttribute("REMOTE_ADDR_SESSION_KEY", ip);
            }

            return ip;
        }
    }

    private static boolean isUnknown(String ip) {
        return !StringUtils.hasLength(ip) || "unknown".equalsIgnoreCase(ip);
    }

    private static String getMultistageReverseProxyIp(String ip) {
        if (ip != null && ip.indexOf(",") > 0) {
            String[] ips = ip.trim().split(",");
            String[] var2 = ips;
            int var3 = ips.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String subIp = var2[var4];
                if (!isUnknown(subIp)) {
                    ip = subIp;
                    break;
                }
            }
        }

        return ip;
    }

    public static String getRequestUri() {
        return request().getRequestURI();
    }

    public static String getUserAgent() {
        return getHeader("user-agent");
    }

    public static String getMenuId() {
        return getHeader("X-Menu-Id");
    }

    public static String getSystemId() {
        return getHeader("X-System-Id");
    }

    public static String getMethod() {
        return request().getMethod();
    }
}
