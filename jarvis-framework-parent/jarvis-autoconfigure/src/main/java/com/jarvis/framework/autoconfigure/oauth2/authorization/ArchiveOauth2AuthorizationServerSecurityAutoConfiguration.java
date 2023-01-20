package com.jarvis.framework.autoconfigure.oauth2.authorization;

import com.jarvis.framework.autoconfigure.oauth2.resource.ArchiveOauth2ResourceServerConfiguration;
import com.jarvis.framework.oauth2.authorization.server.config.Oauth2AuthenticationServerSecurityConfig;
import com.jarvis.framework.oauth2.authorization.server.config.Oauth2ServerProperties;
import com.jarvis.framework.security.authentication.LimitBadCreadentialsDaoAuthenticationProvider;
import com.jarvis.framework.security.authentication.config.BadCreadentialsProperties;
import com.jarvis.framework.security.constant.SecurityConstant;
import com.jarvis.framework.security.service.BadCreadentialsService;
import com.jarvis.framework.security.service.HttpSecurityProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity.IgnoredRequestConfigurer;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Configuration(
    proxyBeanMethods = false
)
@ConditionalOnClass({Oauth2AuthenticationServerSecurityConfig.class})
@Import({ArchiveOauth2AuthorizationServerConfiguration.class, ArchiveOauth2ResourceServerConfiguration.class, ArchiveRedisBadCreadentialsConfiguration.class})
@EnableConfigurationProperties({ArchiveSecurityProperties.class, Oauth2ServerProperties.class})
public class ArchiveOauth2AuthorizationServerSecurityAutoConfiguration {
    public ArchiveOauth2AuthorizationServerSecurityAutoConfiguration() {
    }

    @Configuration(
        proxyBeanMethods = false
    )
    @ConditionalOnClass({Oauth2AuthenticationServerSecurityConfig.class})
    static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private AccessDeniedHandler accessDeniedHandler;
        @Autowired(
            required = false
        )
        private List<SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>> securityConfigurers;
        @Autowired(
            required = false
        )
        private HttpSecurityProcessor httpSecurityProcessor;
        @Autowired
        private BearerTokenResolver bearerTokenResolver;
        @Autowired
        private ArchiveSecurityProperties securityProperties;
        @Autowired
        private OpaqueTokenIntrospector opaqueTokenIntrospector;
        @Autowired
        private AuthenticationEntryPoint authenticationEntryPoint;
        @Autowired(
            required = false
        )
        private List<LogoutHandler> logoutHandlers;
        @Autowired(
            required = false
        )
        private LogoutSuccessHandler logoutSuccessHandler;

        protected void configure(AuthenticationManagerBuilder a) throws Exception {
            LimitBadCreadentialsDaoAuthenticationProvider var2 = new LimitBadCreadentialsDaoAuthenticationProvider();
            UserDetailsService var3 = (UserDetailsService)a.getBeanOrNull(UserDetailsService.class);
            PasswordEncoder var4 = (PasswordEncoder)a.getBeanOrNull(PasswordEncoder.class);
            UserDetailsPasswordService var5 = (UserDetailsPasswordService)a.getBeanOrNull(UserDetailsPasswordService.class);
            BadCreadentialsService var6 = (BadCreadentialsService)a.getBeanOrNull(BadCreadentialsService.class);
            BadCreadentialsProperties var7 = a.securityProperties.getBadCreadentials();
            var2.setUserDetailsService(var3);
            if (null != var4) {
                var2.setPasswordEncoder(var4);
            }

            if (null != var5) {
                var2.setUserDetailsPasswordService(var5);
            }

            if (null != var6) {
                var2.setBadCreadentialsService(var6);
            }

            var2.afterPropertiesSet();
            var2.setBadCreadentialsProperties(var7);
            a.authenticationProvider(var2);
        }

        WebSecurityConfig() {
        }

        public void configure(WebSecurity a) throws Exception {
            List var2 = a.securityProperties.getIgnoreUrls();
            Collections.addAll(var2, SecurityConstant.GLOBAL_IGNORE_URLS);
            IgnoredRequestConfigurer var10000 = a.ignoring();
            String[] var10002 = new String[var2.size()];
            boolean var10004 = true;
            var10000.antMatchers((String[])var2.toArray(var10002));
        }

        protected void configure(HttpSecurity a) throws Exception {
            a.authorize(a);
            if (null != a.securityConfigurers) {
                Iterator var2;
                Iterator var10000 = var2 = a.securityConfigurers.iterator();

                while(var10000.hasNext()) {
                    SecurityConfigurerAdapter var3 = (SecurityConfigurerAdapter)var2.next();
                    var10000 = var2;
                    a.apply(var3);
                }
            }

            ((HttpSecurity)((HttpSecurity)((HttpSecurity)a.csrf().disable()).exceptionHandling().accessDeniedHandler(a.accessDeniedHandler).authenticationEntryPoint(a.authenticationEntryPoint).and()).sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()).oauth2ResourceServer().accessDeniedHandler(a.accessDeniedHandler).authenticationEntryPoint(a.authenticationEntryPoint).bearerTokenResolver(a.bearerTokenResolver).opaqueToken().introspector(a.opaqueTokenIntrospector);
            a.logout(a);
            Optional.ofNullable(a.httpSecurityProcessor).ifPresent((ax) -> {
                ax.process(a);
            });
        }
    }
}
