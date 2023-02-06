package com.jarvis.framework.webmvc.util;

import com.jarvis.framework.constant.WebMvcConstant;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月19日
 */
public abstract class WebUtil {

    public static final String REMOTE_ADDR_SESSION_KEY = "REMOTE_ADDR_SESSION_KEY";

    public static HttpServletRequest request() {
        final ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes());
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
        final String tenantId = getHeader("X-Tenant-Id");
        return null == tenantId ? "1" : tenantId;
    }

    /**
     * 获取客户端IP
     *
     * <p>
     * 默认检测的Header:
     *
     * <pre>
     * 1、X-Forwarded-For
     * 2、X-Real-IP
     * 3、Proxy-Client-IP
     * 4、WL-Proxy-Client-IP
     * 5、HTTP_CLIENT_IP
     * 6、HTTP_X_FORWARDED_FOR
     * </pre>
     *
     * @return IP地址
     */
    public static String getRemoteAddr() {
        String ip = null;
        final HttpSession session = session();
        if (null != session) {
            ip = (String) session.getAttribute(REMOTE_ADDR_SESSION_KEY);
        }
        if (null != ip) {
            return ip;
        }
        final HttpServletRequest request = request();
        final String[] headers = { "X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR" };

        for (final String header : headers) {
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
            session.setAttribute(REMOTE_ADDR_SESSION_KEY, ip);
        }
        return ip;
    }

    private static boolean isUnknown(String ip) {
        return !StringUtils.hasLength(ip) || "unknown".equalsIgnoreCase(ip);
    }

    /**
     * 从多级反向代理中获得第一个非unknown IP地址
     *
     * @param ip 获得的IP地址
     * @return 第一个非unknown IP地址
     */
    private static String getMultistageReverseProxyIp(String ip) {
        // 多级反向代理检测
        if (ip != null && ip.indexOf(",") > 0) {
            final String[] ips = ip.trim().split(",");
            for (final String subIp : ips) {
                if (false == isUnknown(subIp)) {
                    ip = subIp;
                    break;
                }
            }
        }
        return ip;
    }

    /**
     * 请求uri
     *
     * @return
     */
    public static String getRequestUri() {
        return request().getRequestURI();
    }

    /**
     * 请求用户代理
     *
     * @return
     */
    public static String getUserAgent() {
        return getHeader("user-agent");
    }

    /**
     * 菜单ID
     *
     * @return
     */
    public static String getMenuId() {
        return getHeader(WebMvcConstant.MENU_HEADER_NAME);
    }

    /**
     * 系统ID
     *
     * @return
     */
    public static String getSystemId() {
        return getHeader(WebMvcConstant.SYSTEM_HEADER_NAME);
    }

    /**
     * 请求method
     *
     * @return
     */
    public static String getMethod() {
        return request().getMethod();
    }

}
