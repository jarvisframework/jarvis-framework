package com.jarvis.framework.autoconfigure.oauth2.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarvis.framework.oauth2.resource.server.introspection.RedisOpaqueTokenIntrospector;
import com.jarvis.framework.oauth2.server.token.Oauth2TokenObtainService;
import com.jarvis.framework.oauth2.server.token.support.RedisOauth2TokenObtainService;
import com.jarvis.framework.security.authentication.JsonAccessDeniedHandler;
import com.jarvis.framework.security.authentication.JsonAuthenctiationFailureHandler;
import com.jarvis.framework.security.authentication.JsonAuthenticationEntryPoint;
import com.jarvis.framework.security.authentication.JsonAuthenticationSuccessHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class ArchiveOauth2ResourceServerConfiguration {
    @Bean
    @ConditionalOnMissingBean
    Oauth2TokenObtainService oauth2TokenObtainService(RedisConnectionFactory a) {
        return new RedisOauth2TokenObtainService(a);
    }

    @Bean
    @ConditionalOnMissingBean({AuthenticationSuccessHandler.class})
    AuthenticationSuccessHandler authenticationSuccessHandler(ObjectMapper a) {
        return new JsonAuthenticationSuccessHandler(a);
    }

    @Bean
    @ConditionalOnMissingBean
    OpaqueTokenIntrospector opaqueTokenIntrospector(Oauth2TokenObtainService a) {
        return new RedisOpaqueTokenIntrospector(a);
    }

    @Bean
    @ConditionalOnMissingBean({AccessDeniedHandler.class})
    AccessDeniedHandler accessDeniedHandler(ObjectMapper a) {
        return new JsonAccessDeniedHandler(a);
    }

    @Bean
    @ConditionalOnMissingBean({BearerTokenResolver.class})
    BearerTokenResolver bearerTokenResolver() {
        DefaultBearerTokenResolver var1 = new DefaultBearerTokenResolver();
        var1.setAllowUriQueryParameter(true);
        var1.setAllowFormEncodedBodyParameter(true);
        return var1;
    }

    public ArchiveOauth2ResourceServerConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean({AuthenticationEntryPoint.class})
    AuthenticationEntryPoint authenticationEntryPoint(ObjectMapper a) {
        return new JsonAuthenticationEntryPoint(a);
    }

    @Bean
    @ConditionalOnMissingBean({AuthenticationFailureHandler.class})
    AuthenticationFailureHandler authenticationFailureHandler(ObjectMapper a) {
        return new JsonAuthenctiationFailureHandler(a);
    }
}
