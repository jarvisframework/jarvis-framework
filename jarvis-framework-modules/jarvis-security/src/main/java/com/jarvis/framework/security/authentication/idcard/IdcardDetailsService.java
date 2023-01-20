package com.jarvis.framework.security.authentication.idcard;

import com.jarvis.framework.security.model.SecurityUser;

public interface IdcardDetailsService {
    SecurityUser loadSecurityUser(IdcardAuthenticationToken var1);
}
