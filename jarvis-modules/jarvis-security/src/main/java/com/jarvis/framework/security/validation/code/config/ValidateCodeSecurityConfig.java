package com.jarvis.framework.security.validation.code.config;

import com.jarvis.framework.security.validation.code.ValidateCodeProcessorHolder;
import com.jarvis.framework.security.validation.code.web.ValidateCodeFilter;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月26日
 */
public class ValidateCodeSecurityConfig
        extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final AuthenticationFailureHandler authenticationFailureHandler;

    private final ValidateCodeProcessorHolder validateCodeProcessorHolder;

    private final ValidateCodeProperties validateCodeProperties;

    public ValidateCodeSecurityConfig(AuthenticationFailureHandler authenticationFailureHandler,
                                      ValidateCodeProcessorHolder validateCodeProcessorHolder, ValidateCodeProperties validateCodeProperties) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.validateCodeProcessorHolder = validateCodeProcessorHolder;
        this.validateCodeProperties = validateCodeProperties;
    }

    /**
     *
     * @see org.springframework.security.config.annotation.SecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.SecurityBuilder)
     */
    @Override
    public void configure(HttpSecurity builder) throws Exception {
        final ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter(authenticationFailureHandler,
                validateCodeProcessorHolder, validateCodeProperties);
        builder.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
