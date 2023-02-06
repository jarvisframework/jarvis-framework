package com.jarvis.framework.security.authentication.idcard;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月29日
 */
public class IdcardAuthenticationSecurityConfig
        extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    private final AuthenticationFailureHandler authenticationFailureHandler;

    private final IdcardDetailsService idCardDetailService;

    /**
     * @param authenticationSuccessHandler
     * @param authenticationFailureHandler
     */
    public IdcardAuthenticationSecurityConfig(
            AuthenticationSuccessHandler authenticationSuccessHandler,
            AuthenticationFailureHandler authenticationFailureHandler,
            IdcardDetailsService idCardDetailService) {
        super();
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.idCardDetailService = idCardDetailService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        final IdcardAuthenticationFilter idcardAuthenticationFilter = new IdcardAuthenticationFilter();
        idcardAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        idcardAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        idcardAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

        final IdcardAuthenticationProvider idcardAuthenticationProvider = new IdcardAuthenticationProvider(
                idCardDetailService);

        http.authenticationProvider(idcardAuthenticationProvider)
                .addFilterAfter(idcardAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

}
