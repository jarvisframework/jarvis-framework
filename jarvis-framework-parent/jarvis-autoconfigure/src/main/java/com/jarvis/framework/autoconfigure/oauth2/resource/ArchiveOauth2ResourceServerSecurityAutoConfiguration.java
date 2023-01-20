package com.jarvis.framework.autoconfigure.oauth2.resource;

import com.jarvis.framework.oauth2.resource.server.config.Oauth2ResourceServerSecurityConfig;
import com.jarvis.framework.security.constant.SecurityConstant;
import com.jarvis.framework.security.service.HttpSecurityProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity.IgnoredRequestConfigurer;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Configuration(
    proxyBeanMethods = false
)
@ConditionalOnClass({Oauth2ResourceServerSecurityConfig.class})
@EnableConfigurationProperties({ArchiveSecurityProperties.class})
@Import({ArchiveOauth2ResourceServerConfiguration.class})
@ConditionalOnMissingClass({"com.gdda.archives.framework.oauth2.authorization.server.config.Oauth2AuthenticationServerSecurityConfig"})
public class ArchiveOauth2ResourceServerSecurityAutoConfiguration {
    public ArchiveOauth2ResourceServerSecurityAutoConfiguration() {
    }

    @Configuration(
        proxyBeanMethods = false
    )
    @ConditionalOnClass({Oauth2ResourceServerSecurityConfig.class})
    @ConditionalOnMissingClass({"com.gdda.archives.framework.oauth2.authorization.server.config.Oauth2AuthenticationServerSecurityConfig"})
    static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired(
            required = false
        )
        private List<SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>> securityConfigurers;
        @Autowired
        private AuthenticationEntryPoint authenticationEntryPoint;
        @Autowired
        private ArchiveSecurityProperties securityProperties;
        @Autowired
        private OpaqueTokenIntrospector opaqueTokenIntrospector;
        @Autowired
        private BearerTokenResolver bearerTokenResolver;
        @Autowired(
            required = false
        )
        private HttpSecurityProcessor httpSecurityProcessor;
        @Autowired
        private AccessDeniedHandler accessDeniedHandler;

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
            Optional.ofNullable(a.httpSecurityProcessor).ifPresent((ax) -> {
                ax.process(a);
            });
        }

        WebSecurityConfig() {
        }
    }
}
