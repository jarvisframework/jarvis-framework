package com.jarvis.framework.bizlog.util;

import com.jarvis.framework.webmvc.util.WebUtil;
import com.jarvis.framework.bizlog.entity.BizLog;
import com.jarvis.framework.security.model.SecurityUser;
import com.jarvis.framework.security.util.SecurityUtil;

public abstract class BizLoggerUtil {
    public BizLoggerUtil() {
    }

    public static BizLog createRequestBizLog() {
        BizLog entity = new BizLog();
        entity.setRemoteAddr(WebUtil.getRemoteAddr());
        entity.setMethod(WebUtil.getMethod());
        entity.setRequestUri(WebUtil.getRequestUri());
        entity.setUserAgent(WebUtil.getUserAgent());
        entity.setSystemId(WebUtil.getSystemId());
        SecurityUser user = SecurityUtil.getUser();
        if (user != null) {
            if (user.getUserDetails() != null) {
                entity.setUserDetails(user.getUserDetails());
            } else {
                entity.setUserDetails(user);
            }
        }

        return entity;
    }
}
