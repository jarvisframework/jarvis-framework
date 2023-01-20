package com.jarvis.framework.security.authentication.sso;

import com.jarvis.framework.security.model.SecurityUser;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class SsoAuthenticationProvider implements AuthenticationProvider {
    private final SsoDetailsService ssoDetailsService;

    public SsoAuthenticationProvider(SsoDetailsService ssoDetailsService) {
        this.ssoDetailsService = ssoDetailsService;
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SsoAuthenticationToken authenticationToken = (SsoAuthenticationToken)authentication;
        SecurityUser user = this.toSecurityUser(authenticationToken);
        SsoAuthenticationToken authenticationResult = new SsoAuthenticationToken(user, authenticationToken.getSsoTokenModel(), user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    private SecurityUser toSecurityUser(SsoAuthenticationToken token) {
        return this.ssoDetailsService.loadSecurityUser(token);
    }

    public boolean supports(Class<?> authentication) {
        return SsoAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
