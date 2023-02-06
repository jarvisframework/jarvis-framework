package com.jarvis.framework.security.authentication.idcard;

import com.jarvis.framework.security.model.SecurityUser;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年3月3日
 */
public interface IdcardDetailsService {

    SecurityUser loadSecurityUser(IdcardAuthenticationToken token);

}
