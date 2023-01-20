package com.jarvis.framework.autoconfigure.oauth2.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarvis.framework.autoconfigure.mybatis.DruidExtendProperties;
import com.jarvis.framework.oauth2.authorization.server.authentication.Oauth2AuthenticationSuccessHandler;
import com.jarvis.framework.oauth2.authorization.server.authentication.Oauth2LogoutHandler;
import com.jarvis.framework.oauth2.authorization.server.config.Oauth2AuthenticationServerSecurityConfig;
import com.jarvis.framework.oauth2.authorization.server.config.Oauth2ServerConfigService;
import com.jarvis.framework.oauth2.authorization.server.config.Oauth2ServerProperties;
import com.jarvis.framework.oauth2.authorization.server.token.DefaultOauth2TokenCreationService;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

public class ArchiveOauth2AuthorizationServerConfiguration {
    @Bean
    Oauth2LogoutHandler oauth2LogoutHandler(Oauth2TokenStoreService a, BearerTokenResolver a) {
        return new Oauth2LogoutHandler(a, a);
    }

    @Bean
    @ConditionalOnMissingBean({AuthenticationFailureHandler.class})
    AuthenticationFailureHandler authenticationFailureHandler(ObjectMapper a) {
        return new JsonAuthenctiationFailureHandler(a);
    }

    @Bean
    Oauth2AuthenticationServerSecurityConfig oauth2AuthenticationServerConfig(AuthenticationSuccessHandler a, AuthenticationFailureHandler a) {
        return new Oauth2AuthenticationServerSecurityConfig(a, a);
    }

    @Bean
    @ConditionalOnMissingBean({PasswordEncoder.class})
    PasswordEncoder passwordEncoder() {
        DelegatingPasswordEncoder var1 = (DelegatingPasswordEncoder)PasswordEncoderFactories.createDelegatingPasswordEncoder();
        MessageDigestPasswordEncoder var2 = new MessageDigestPasswordEncoder(DruidExtendProperties.oOoOOo("b\u001cpy\u0003a\u0007"));
        var1.setDefaultPasswordEncoderForMatches(var2);
        return var1;
    }

    @Bean
    ValidateCodeStoreService validateCodeStoreService(RedisTemplate<Object, Object> a) {
        return new RedisValidateCodeStoreService(a);
    }

    @Bean
    @ConditionalOnMissingBean({LogoutSuccessHandler.class})
    LogoutSuccessHandler logoutSuccessHandler(ObjectMapper a) {
        return new JsonLogoutSuccessHandler(a);
    }

    @Bean
    @ConditionalOnMissingBean({AuthenticationSuccessHandler.class})
    AuthenticationSuccessHandler authenticationSuccessHandler(ObjectMapper a, Oauth2TokenStoreService a, Oauth2ServerProperties a) {
        Oauth2TokenStoreService a = new DefaultOauth2TokenCreationService(a, a);
        return new Oauth2AuthenticationSuccessHandler(a, a);
    }

    @Bean
    @ConditionalOnMissingBean({UserDetailsService.class})
    UserDetailsService userDetailsService(final PasswordEncoder a) {
        return new UserDetailsService() {
            protected final Log logger;
            private final String password;

            {
                ax.logger = LogFactory.getLog(ax.getClass());
                ax.password = a.encode(ArchiveValidateCodeAutoConfiguration.oOoOOo("\u0001i\u0001i\u0001i"));
            }

            public UserDetails loadUserByUsername(String axx) throws UsernameNotFoundException {
                long var2 = System.currentTimeMillis();
                if (!DruidExtendProperties.oOoOOo("'D$T&P0\\=_").equals(axx) && !axx.startsWith(ArchiveValidateCodeAutoConfiguration.oOoOOo("8U4X7"))) {
                    throw new UsernameNotFoundException(DruidExtendProperties.oOoOOo("畼戆呙戧宒砰镍诞"));
                } else {
                    SecurityUser var4 = new SecurityUser();
                    var4.setId(axx);
                    var4.setUsername(axx);
                    var4.setPassword(ax.password);
                    var4.setShowName(ArchiveValidateCodeAutoConfiguration.oOoOOo("跜纖篸琷吁"));
                    GrantedAuthority[] var10001 = new GrantedAuthority[1];
                    boolean var10003 = true;
                    boolean var10006 = false;
                    var10001[0] = new SimpleGrantedAuthority(DruidExtendProperties.oOoOOo("'D$T&P0\\=_"));
                    var4.addAuthorities(var10001);
                    if (ax.logger.isDebugEnabled()) {
                        Log var10000 = ax.logger;
                        String var5 = ArchiveValidateCodeAutoConfiguration.oOoOOo("瘢彤\u0002]6P=d*T+s d*T+_8\\<l奝琷儨耦斯Ｋ|By\\*");
                        Object[] var10002 = new Object[1];
                        boolean var10004 = true;
                        var10002[0] = System.currentTimeMillis() - var2;
                        var10000.debug(String.format(var5, var10002));
                    }

                    return var4;
                }
            }
        };
    }

    public ArchiveOauth2AuthorizationServerConfiguration() {
    }

    @Bean
    Oauth2ServerConfigService oauth2ServerConfigService(RedisConnectionFactory a, Oauth2ServerProperties a) {
        return new Oauth2ServerConfigService(a, a);
    }

    @Bean
    Oauth2TokenStoreService redisOauth2TokenStoreService(RedisConnectionFactory a, Oauth2ServerProperties a) {
        return new RedisOauth2TokenStoreService(a, a);
    }
}
