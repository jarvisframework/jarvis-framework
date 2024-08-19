package com.jarvis.framework.autoconfigure.oauth2.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarvis.framework.oauth2.resource.server.introspection.RedisOpaqueTokenIntrospector;
import com.jarvis.framework.oauth2.resource.server.resolver.Oauth2BearerTokenResolver;
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
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月25日
 */
public class JarvisOauth2ResourceServerConfiguration {

    @Bean
    @ConditionalOnMissingBean
    Oauth2TokenObtainService oauth2TokenObtainService(RedisConnectionFactory redisConnectionFactory) {
        return new RedisOauth2TokenObtainService(redisConnectionFactory);
    }

    @Bean
    @ConditionalOnMissingBean
    OpaqueTokenIntrospector opaqueTokenIntrospector(Oauth2TokenObtainService oauth2TokenObtainService) {
        return new RedisOpaqueTokenIntrospector(oauth2TokenObtainService);
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationSuccessHandler.class)
    AuthenticationSuccessHandler authenticationSuccessHandler(ObjectMapper objectMapper) {
        return new JsonAuthenticationSuccessHandler(objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationFailureHandler.class)
    AuthenticationFailureHandler authenticationFailureHandler(ObjectMapper objectMapper) {
        return new JsonAuthenctiationFailureHandler(objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationEntryPoint.class)
    AuthenticationEntryPoint authenticationEntryPoint(ObjectMapper objectMapper) {
        return new JsonAuthenticationEntryPoint(objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(AccessDeniedHandler.class)
    AccessDeniedHandler accessDeniedHandler(ObjectMapper objectMapper) {
        return new JsonAccessDeniedHandler(objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(BearerTokenResolver.class)
    BearerTokenResolver bearerTokenResolver() {
        final Oauth2BearerTokenResolver bearerTokenResolver = new Oauth2BearerTokenResolver();
        bearerTokenResolver.setAllowUriQueryParameter(true);
        bearerTokenResolver.setAllowFormEncodedBodyParameter(true);
        return bearerTokenResolver;
    }

}
