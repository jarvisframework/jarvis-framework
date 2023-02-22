package com.jarvis.framework.security.authentication.sso;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年3月18日
 */
public class SsoAuthenticationSecurityConfig
        extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final SsoDetailsService ssoDetailsService;

    /**
     * @param authenticationEntryPoint
     * @param ssoDetailsService
     */
    public SsoAuthenticationSecurityConfig(
            AuthenticationEntryPoint authenticationEntryPoint,
            SsoDetailsService ssoDetailsService) {
        super();
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.ssoDetailsService = ssoDetailsService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        final AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        final SsoAuthenticationFilter ssoAuthenticationFilter = new SsoAuthenticationFilter(
                authenticationManager, authenticationEntryPoint);

        final SsoAuthenticationProvider ssoAuthenticationProvider = new SsoAuthenticationProvider(
                ssoDetailsService);

        http.authenticationProvider(ssoAuthenticationProvider)
                .addFilterAfter(ssoAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

}
