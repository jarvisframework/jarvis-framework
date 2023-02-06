package com.jarvis.framework.security.authentication.sso;


import com.jarvis.framework.security.model.SecurityUser;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年3月18日
 */
public interface SsoDetailsService {

    /**
     * 获取用户信息
     *
     * @param token token
     * @return SecurityUser
     */
    SecurityUser loadSecurityUser(SsoAuthenticationToken token);

}
