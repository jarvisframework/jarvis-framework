package com.jarvis.framework.security.authentication.sso;


import com.jarvis.framework.security.model.SecurityUser;

public interface SsoDetailsService {
    SecurityUser loadSecurityUser(SsoAuthenticationToken var1);
}
