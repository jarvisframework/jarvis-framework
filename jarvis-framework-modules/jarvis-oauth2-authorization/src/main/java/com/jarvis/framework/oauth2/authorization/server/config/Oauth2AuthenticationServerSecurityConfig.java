package com.jarvis.framework.oauth2.authorization.server.config;

import com.jarvis.framework.oauth2.authorization.server.web.Oauth2TokenAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class Oauth2AuthenticationServerSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    public Oauth2AuthenticationServerSecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public void configure(HttpSecurity http) throws Exception {
        Oauth2TokenAuthenticationFilter oauth2TokenAuthenticationFilter = new Oauth2TokenAuthenticationFilter();
        oauth2TokenAuthenticationFilter.setAuthenticationManager((AuthenticationManager)http.getSharedObject(AuthenticationManager.class));
        oauth2TokenAuthenticationFilter.setAuthenticationSuccessHandler(this.authenticationSuccessHandler);
        oauth2TokenAuthenticationFilter.setAuthenticationFailureHandler(this.authenticationFailureHandler);
        http.addFilterAfter(oauth2TokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
