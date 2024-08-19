package com.jarvis.framework.autoconfigure.oauth2.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarvis.framework.oauth2.authorization.server.authentication.Oauth2AuthenticationSuccessHandler;
import com.jarvis.framework.oauth2.authorization.server.authentication.Oauth2LogoutHandler;
import com.jarvis.framework.oauth2.authorization.server.config.Oauth2AuthenticationServerSecurityConfig;
import com.jarvis.framework.oauth2.authorization.server.config.Oauth2ServerConfigService;
import com.jarvis.framework.oauth2.authorization.server.config.Oauth2ServerProperties;
import com.jarvis.framework.oauth2.authorization.server.token.DefaultOauth2TokenCreationService;
import com.jarvis.framework.oauth2.authorization.server.token.Oauth2TokenCreationService;
import com.jarvis.framework.oauth2.authorization.server.token.Oauth2TokenStoreService;
import com.jarvis.framework.oauth2.authorization.server.token.support.RedisOauth2TokenStoreService;
import com.jarvis.framework.oauth2.authorization.server.web.RedisValidateCodeStoreService;
import com.jarvis.framework.security.authentication.JsonAuthenctiationFailureHandler;
import com.jarvis.framework.security.authentication.JsonLogoutSuccessHandler;
import com.jarvis.framework.security.model.SecurityUser;
import com.jarvis.framework.security.validation.code.ValidateCodeStoreService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月23日
 */
public class JarvisOauth2AuthorizationServerConfiguration {

    @Bean
    Oauth2TokenStoreService redisOauth2TokenStoreService(RedisConnectionFactory redisConnectionFactory,
                                                         Oauth2ServerProperties oauth2ServerProperties) {
        final RedisOauth2TokenStoreService tokenStoreService = new RedisOauth2TokenStoreService(
                redisConnectionFactory, oauth2ServerProperties);

        return tokenStoreService;
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationSuccessHandler.class)
    AuthenticationSuccessHandler authenticationSuccessHandler(ObjectMapper objectMapper,
                                                              Oauth2TokenStoreService tokenStoreService, Oauth2ServerProperties properties) {
        final Oauth2TokenCreationService tokenCreationService = new DefaultOauth2TokenCreationService(tokenStoreService,
                properties);
        return new Oauth2AuthenticationSuccessHandler(objectMapper, tokenCreationService);
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationFailureHandler.class)
    AuthenticationFailureHandler authenticationFailureHandler(ObjectMapper objectMapper) {
        return new JsonAuthenctiationFailureHandler(objectMapper);
    }

    @Bean
    Oauth2AuthenticationServerSecurityConfig oauth2AuthenticationServerConfig(
        /*Oauth2TokenStoreService tokenStoreService,
        Oauth2ServerProperties oauth2ServerProperties, */AuthenticationSuccessHandler authenticationSuccessHandler,
                                                         AuthenticationFailureHandler authenticationFailureHandler) {
        return new Oauth2AuthenticationServerSecurityConfig(
            /*oauth2ServerProperties.getAccessTokenTimeout(),
            tokenStoreService,*/ authenticationSuccessHandler, authenticationFailureHandler);
    }

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        return new UserDetailsService() {

            protected final Log logger = LogFactory.getLog(getClass());

            private final String password = passwordEncoder.encode("000000");

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                final long start = System.currentTimeMillis();
                if (!"superadmin".equals(username) && !username.startsWith("admin")) {
                    throw new UsernameNotFoundException("用户名或密码错误");
                }
                final SecurityUser user = new SecurityUser();
                user.setId(username);
                user.setUsername(username);
                user.setPassword(password);
                user.setShowName("超级管理员");
                user.addAuthorities(new SimpleGrantedAuthority("superadmin"));
                if (logger.isDebugEnabled()) {
                    logger.debug(
                            String.format("登录[loadUserByUsername]处理共耗时：%s ms", (System.currentTimeMillis() - start)));
                }
                return user;
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    PasswordEncoder passwordEncoder() {

        final DelegatingPasswordEncoder delegatingPasswordEncoder = (DelegatingPasswordEncoder) PasswordEncoderFactories
                .createDelegatingPasswordEncoder();
        final PasswordEncoder d = new org.springframework.security.crypto.password.MessageDigestPasswordEncoder(
                "SHA-256");

        delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(d);

        return delegatingPasswordEncoder;
    }

    @Bean
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    LogoutSuccessHandler logoutSuccessHandler(ObjectMapper objectMapper) {
        return new JsonLogoutSuccessHandler(objectMapper);
    }

    @Bean
    Oauth2LogoutHandler oauth2LogoutHandler(Oauth2TokenStoreService oauth2TokenStoreService,
                                            BearerTokenResolver bearerTokenResolver) {
        return new Oauth2LogoutHandler(oauth2TokenStoreService, bearerTokenResolver);
    }

    @Bean
        //@ConditionalOnProperty(prefix = "spring.security.validate-code", name = "enabled", havingValue = "true")
    ValidateCodeStoreService validateCodeStoreService(RedisTemplate<Object, Object> redisTemplate) {
        return new RedisValidateCodeStoreService(redisTemplate);
    }

    @Bean
    Oauth2ServerConfigService oauth2ServerConfigService(RedisConnectionFactory redisConnectionFactory,
                                                        Oauth2ServerProperties oauth2ServerProperties) {
        return new Oauth2ServerConfigService(redisConnectionFactory, oauth2ServerProperties);
    }

}
