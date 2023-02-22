package com.jarvis.framework.security.authentication.sso;

import com.jarvis.framework.security.model.SecurityUser;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年3月18日
 */
public class SsoAuthenticationProvider implements AuthenticationProvider {

    private final SsoDetailsService ssoDetailsService;

    public SsoAuthenticationProvider(SsoDetailsService ssoDetailsService) {
        this.ssoDetailsService = ssoDetailsService;
    }

    /**
     *
     * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final SsoAuthenticationToken authenticationToken = (SsoAuthenticationToken) authentication;
        final SecurityUser user = toSecurityUser(authenticationToken);
        final SsoAuthenticationToken authenticationResult = new SsoAuthenticationToken(user,
                authenticationToken.getSsoTokenModel(),
                user.getAuthorities());

        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    private SecurityUser toSecurityUser(SsoAuthenticationToken token) {
        return ssoDetailsService.loadSecurityUser(token);
    }

    /**
     *
     * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return SsoAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
