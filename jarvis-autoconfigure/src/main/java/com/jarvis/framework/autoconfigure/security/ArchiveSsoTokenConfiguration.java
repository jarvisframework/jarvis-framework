package com.jarvis.framework.autoconfigure.security;

import com.jarvis.framework.security.authentication.sso.SsoAuthenticationSecurityConfig;
import com.jarvis.framework.security.authentication.sso.SsoDetailsService;
import com.jarvis.framework.security.authentication.sso.service.InMemorySsoDetailsService;
import com.jarvis.framework.security.validation.code.web.ValidateCodeEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年3月21日
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "spring.security.sso-token", name = "enabled", havingValue = "true")
@ConditionalOnClass({ ValidateCodeEndpoint.class })
public class ArchiveSsoTokenConfiguration {

    @Bean
    @ConditionalOnMissingBean(SsoDetailsService.class)
    SsoDetailsService ssoDetailsService(ArchiveSecurityProperties properties) {
        final SsoTokenProperties ssoToken = properties.getSsoToken();
        return new InMemorySsoDetailsService(ssoToken.getUsers());
    }

    @Bean
    SsoAuthenticationSecurityConfig ssoAuthenticationSecurityConfig(
            AuthenticationEntryPoint authenticationEntryPoint, SsoDetailsService ssoDetailsService) {
        return new SsoAuthenticationSecurityConfig(authenticationEntryPoint, ssoDetailsService);
    }
}
