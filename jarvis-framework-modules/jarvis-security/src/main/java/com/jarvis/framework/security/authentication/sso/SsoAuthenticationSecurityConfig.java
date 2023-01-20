package com.jarvis.framework.security.authentication.sso;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SsoAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final SsoDetailsService ssoDetailsService;

    public SsoAuthenticationSecurityConfig(AuthenticationEntryPoint authenticationEntryPoint, SsoDetailsService ssoDetailsService) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.ssoDetailsService = ssoDetailsService;
    }

    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = (AuthenticationManager)http.getSharedObject(AuthenticationManager.class);
        SsoAuthenticationFilter ssoAuthenticationFilter = new SsoAuthenticationFilter(authenticationManager, this.authenticationEntryPoint);
        SsoAuthenticationProvider ssoAuthenticationProvider = new SsoAuthenticationProvider(this.ssoDetailsService);
        http.authenticationProvider(ssoAuthenticationProvider).addFilterAfter(ssoAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
