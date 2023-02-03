package com.jarvis.framework.autoconfigure.oauth2.resource;

import com.jarvis.framework.autoconfigure.security.ArchiveSecurityProperties;
import com.jarvis.framework.constant.WebMvcConstant;
import com.jarvis.framework.oauth2.resource.server.config.Oauth2ResourceServerSecurityConfig;
import com.jarvis.framework.security.access.PermitAssignedHeaderVoter;
import com.jarvis.framework.security.constant.SecurityConstant;
import com.jarvis.framework.security.service.HttpSecurityProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月26日
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ Oauth2ResourceServerSecurityConfig.class })
@EnableConfigurationProperties({ ArchiveSecurityProperties.class })
@Import(ArchiveOauth2ResourceServerConfiguration.class)
@ConditionalOnMissingClass("com.gdda.archives.framework.oauth2.authorization.server.config.Oauth2AuthenticationServerSecurityConfig")
public class ArchiveOauth2ResourceServerSecurityAutoConfiguration {

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass({ Oauth2ResourceServerSecurityConfig.class })
    @ConditionalOnMissingClass("com.gdda.archives.framework.oauth2.authorization.server.config.Oauth2AuthenticationServerSecurityConfig")
    static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private BearerTokenResolver bearerTokenResolver;

        @Autowired
        private OpaqueTokenIntrospector opaqueTokenIntrospector;

        @Autowired
        private AuthenticationEntryPoint authenticationEntryPoint;

        @Autowired
        private AccessDeniedHandler accessDeniedHandler;

        @Autowired(required = false)
        private List<SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>> securityConfigurers;

        @Autowired
        private ArchiveSecurityProperties securityProperties;

        @Autowired(required = false)
        private HttpSecurityProcessor httpSecurityProcessor;

        @Override
        public void configure(WebSecurity web) throws Exception {
            final List<String> ignoreUrls = securityProperties.getIgnoreUrls();
            Collections.addAll(ignoreUrls, SecurityConstant.GLOBAL_IGNORE_URLS);
            web.ignoring().antMatchers(ignoreUrls.toArray(new String[ignoreUrls.size()]));
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //
            authorize(http);

            if (null != securityConfigurers) {
                for (final SecurityConfigurerAdapter<DefaultSecurityFilterChain,
                        HttpSecurity> sc : securityConfigurers) {
                    http.apply(sc);
                }
            }

            http.csrf()
                    .disable()
                    .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler)
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .oauth2ResourceServer()
                    .accessDeniedHandler(accessDeniedHandler)
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .bearerTokenResolver(bearerTokenResolver)
                    .opaqueToken().introspector(opaqueTokenIntrospector);

            // 定制化HttpSecurity配置
            Optional.ofNullable(httpSecurityProcessor).ifPresent((p) -> {
                p.process(http);
            });
        }

        private void authorize(HttpSecurity http) throws Exception {
            final ExpressionUrlAuthorizationConfigurer<
                                HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests = http
                    .authorizeRequests();
            if (null != securityProperties.getAuthorityUrls() || !securityProperties.getAuthorityUrls().isEmpty()) {
                securityProperties.getAuthorityUrls().forEach(authorityUrl -> {
                    authorizeRequests.antMatchers(authorityUrl.getUrl()).hasAnyAuthority(authorityUrl.getAuthorities());
                });
            }

            if (null != securityProperties.getDenyUrls() || !securityProperties.getDenyUrls().isEmpty()) {
                final String[] arrayUrl = securityProperties.getDenyUrls()
                        .toArray(new String[securityProperties.getDenyUrls().size()]);
                authorizeRequests.antMatchers(arrayUrl).denyAll();
            }

            if (null != securityProperties.getPermitUrls() || !securityProperties.getPermitUrls().isEmpty()) {
                final String[] arrayUrl = securityProperties.getPermitUrls()
                        .toArray(new String[securityProperties.getPermitUrls().size()]);
                authorizeRequests.antMatchers(arrayUrl).permitAll();
            }

            authorizeRequests.anyRequest().authenticated();

            authorizeRequests.withObjectPostProcessor(new ObjectPostProcessor<AffirmativeBased>() {

                @Override
                public <O extends AffirmativeBased> O postProcess(O object) {

                    object.getDecisionVoters().add(new PermitAssignedHeaderVoter(WebMvcConstant.PERMIT_HEADER_NAME,
                            securityProperties.getPermitAccessId()));
                    return object;
                }

            });
        }

    }

}
