package com.jarvis.framework.security.authentication.idcard;

import com.jarvis.framework.security.model.SecurityUser;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月28日
 */
public class IdcardAuthenticationProvider implements AuthenticationProvider {

    private final IdcardDetailsService idCardDetailService;

    public IdcardAuthenticationProvider(IdcardDetailsService idCardDetailService) {
        this.idCardDetailService = idCardDetailService;
    }

    /**
     *
     * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final IdcardAuthenticationToken authenticationToken = (IdcardAuthenticationToken) authentication;
        final SecurityUser user = toSecurityUser(authenticationToken);
        final IdcardAuthenticationToken authenticationResult = new IdcardAuthenticationToken(user, null,
                user.getAuthorities());

        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    private SecurityUser toSecurityUser(IdcardAuthenticationToken token) {
        /*final SecurityUser user = new SecurityUser();
        final IdcardModel idcardMode = token.getIdcardMode();
        user.setShowName(idcardMode.getName());
        user.setUsername(idcardMode.getIdCardNumber());
        user.setSex(idcardMode.getSex());
        user.setBirthDate(idcardMode.getBirthDate());
        user.setNationality(idcardMode.getNationality());
        user.setAddress(idcardMode.getAddress());
        user.setId(token.getPrincipal().toString());

        user.addAuthorities(AuthorityUtils.createAuthorityList("user"));

        return user;*/
        return idCardDetailService.loadSecurityUser(token);
    }

    /**
     *
     * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return IdcardAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
