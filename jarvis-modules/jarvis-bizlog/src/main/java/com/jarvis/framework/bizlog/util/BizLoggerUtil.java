package com.jarvis.framework.bizlog.util;

import com.jarvis.framework.bizlog.entity.BizLog;
import com.jarvis.framework.security.model.SecurityUser;
import com.jarvis.framework.security.util.SecurityUtil;
import com.jarvis.framework.webmvc.util.WebUtil;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年7月19日
 */
public abstract class BizLoggerUtil {

    public static BizLog createRequestBizLog() {

        final BizLog entity = new BizLog();

        entity.setRemoteAddr(WebUtil.getRemoteAddr());
        entity.setMethod(WebUtil.getMethod());
        entity.setRequestUri(WebUtil.getRequestUri());
        entity.setUserAgent(WebUtil.getUserAgent());
        entity.setSystemId(WebUtil.getSystemId());
        final SecurityUser user = SecurityUtil.getUser();
        if (null != user) {
            if (null != user.getUserDetails()) {
                entity.setUserDetails(user.getUserDetails());
            } else {
                entity.setUserDetails(user);
            }
        }

        return entity;
    }

}
