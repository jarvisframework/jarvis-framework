package com.jarvis.framework.security.authentication.idcard;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class IdcardAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final IdcardDetailsService idCardDetailService;

    public IdcardAuthenticationSecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler, IdcardDetailsService idCardDetailService) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.idCardDetailService = idCardDetailService;
    }

    public void configure(HttpSecurity http) throws Exception {
        IdcardAuthenticationFilter idcardAuthenticationFilter = new IdcardAuthenticationFilter();
        idcardAuthenticationFilter.setAuthenticationManager((AuthenticationManager)http.getSharedObject(AuthenticationManager.class));
        idcardAuthenticationFilter.setAuthenticationSuccessHandler(this.authenticationSuccessHandler);
        idcardAuthenticationFilter.setAuthenticationFailureHandler(this.authenticationFailureHandler);
        IdcardAuthenticationProvider idcardAuthenticationProvider = new IdcardAuthenticationProvider(this.idCardDetailService);
        http.authenticationProvider(idcardAuthenticationProvider).addFilterAfter(idcardAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
