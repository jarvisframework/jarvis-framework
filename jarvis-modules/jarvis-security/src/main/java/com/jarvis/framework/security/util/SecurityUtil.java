package com.jarvis.framework.security.util;

import com.jarvis.framework.security.model.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月29日
 */
public class SecurityUtil {

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    public static SecurityUser getUser() {
        final SecurityContext context = SecurityContextHolder.getContext();
        final Authentication authentication = context.getAuthentication();
        if (null == authentication || !(authentication.getPrincipal() instanceof SecurityUser)) {
            return null;
        }
        return (SecurityUser) authentication.getPrincipal();
    }
}
