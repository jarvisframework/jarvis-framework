package com.jarvis.framework.security.util;

import com.jarvis.framework.security.model.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static SecurityUser getUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return null != authentication && authentication.getPrincipal() instanceof SecurityUser ? (SecurityUser) authentication.getPrincipal() : null;
    }
}
