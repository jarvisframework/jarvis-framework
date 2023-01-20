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

@Configuration(
    proxyBeanMethods = false
)
@ConditionalOnProperty(
    prefix = "spring.security.sso-token",
    name = {"enabled"},
    havingValue = "true"
)
@ConditionalOnClass({ValidateCodeEndpoint.class})
public class ArchiveSsoTokenConfiguration {
    public ArchiveSsoTokenConfiguration() {
    }

    @Bean
    SsoAuthenticationSecurityConfig ssoAuthenticationSecurityConfig(AuthenticationEntryPoint a, SsoDetailsService a) {
        return new SsoAuthenticationSecurityConfig(a, a);
    }

    @Bean
    @ConditionalOnMissingBean({SsoDetailsService.class})
    SsoDetailsService ssoDetailsService(ArchiveSecurityProperties a) {
        ArchiveSecurityProperties a = a.getSsoToken();
        return new InMemorySsoDetailsService(a.getUsers());
    }
}
