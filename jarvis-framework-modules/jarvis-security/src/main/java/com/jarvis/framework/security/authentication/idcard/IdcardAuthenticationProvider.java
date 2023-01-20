package com.jarvis.framework.security.authentication.idcard;

import com.jarvis.framework.security.model.SecurityUser;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class IdcardAuthenticationProvider implements AuthenticationProvider {

    private final IdcardDetailsService idCardDetailService;

    public IdcardAuthenticationProvider(IdcardDetailsService idCardDetailService) {
        this.idCardDetailService = idCardDetailService;
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        IdcardAuthenticationToken authenticationToken = (IdcardAuthenticationToken)authentication;
        SecurityUser user = this.toSecurityUser(authenticationToken);
        IdcardAuthenticationToken authenticationResult = new IdcardAuthenticationToken(user, (IdcardModel)null, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    private SecurityUser toSecurityUser(IdcardAuthenticationToken token) {
        return this.idCardDetailService.loadSecurityUser(token);
    }

    public boolean supports(Class<?> authentication) {
        return IdcardAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
