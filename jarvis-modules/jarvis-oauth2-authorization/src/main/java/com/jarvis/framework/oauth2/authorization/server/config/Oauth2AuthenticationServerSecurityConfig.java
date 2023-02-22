package com.jarvis.framework.oauth2.authorization.server.config;

import com.jarvis.framework.oauth2.authorization.server.web.Oauth2TokenAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月23日
 */
public class Oauth2AuthenticationServerSecurityConfig
        extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    //private final int accessTokenTimeout;

    //private final Oauth2TokenStoreService oauth2TokenStoreService;

    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    private final AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * @param oauth2ServerProperties
     * @param oauth2TokenStoreService
     */
    public Oauth2AuthenticationServerSecurityConfig(/*int accessTokenTimeout,
                                                    Oauth2TokenStoreService oauth2TokenStoreService,*/
            AuthenticationSuccessHandler authenticationSuccessHandler,
            AuthenticationFailureHandler authenticationFailureHandler) {
        super();
        //this.accessTokenTimeout = accessTokenTimeout;
        //this.oauth2TokenStoreService = oauth2TokenStoreService;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        final Oauth2TokenAuthenticationFilter oauth2TokenAuthenticationFilter = new Oauth2TokenAuthenticationFilter();
        oauth2TokenAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        //final Oauth2TokenCreationService tokenCreationService = new DefaultOauth2TokenCreationService(
        //    oauth2TokenStoreService, accessTokenTimeout);

        //oauth2TokenAuthenticationFilter.setOauth2TokenCreationService(tokenCreationService);
        oauth2TokenAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        oauth2TokenAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

        http.addFilterAfter(oauth2TokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

}
