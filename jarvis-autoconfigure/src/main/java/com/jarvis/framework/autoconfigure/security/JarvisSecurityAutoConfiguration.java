package com.jarvis.framework.autoconfigure.security;

import com.jarvis.framework.security.authentication.LimitBadCreadentialsDaoAuthenticationProvider;
import com.jarvis.framework.security.authentication.config.BadCreadentialsProperties;
import com.jarvis.framework.security.constant.SecurityConstant;
import com.jarvis.framework.security.service.AccountLockService;
import com.jarvis.framework.security.service.BadCreadentialsService;
import com.jarvis.framework.security.service.HttpSecurityProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月29日
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ WebSecurityConfigurerAdapter.class })
@ConditionalOnMissingClass("com.jarvis.framework.oauth2.resource.server.config.Oauth2ResourceServerSecurityConfig")
@EnableConfigurationProperties(JarvisSecurityProperties.class)
@Import({ JarvisSecurityConfiguration.class, JarvisRedisBadCreadentialsConfiguration.class,
        JarvisSsoTokenConfiguration.class })
public class JarvisSecurityAutoConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired(required = false)
    private List<SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>> securityConfigurers;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    @Autowired
    private JarvisSecurityProperties securityProperties;

    @Autowired(required = false)
    private List<LogoutHandler> logoutHandlers;

    @Autowired(required = false)
    private HttpSecurityProcessor httpSecurityProcessor;

    @Override
    public void configure(WebSecurity web) throws Exception {
        final List<String> ignoreUrls = new ArrayList<>(securityProperties.getIgnoreUrls());
        Collections.addAll(ignoreUrls, SecurityConstant.GLOBAL_IGNORE_URLS);
        web.ignoring().antMatchers(ignoreUrls.toArray(new String[ignoreUrls.size()]));
    }

    /**
     *
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        final BadCreadentialsProperties badCreadentials = securityProperties.getBadCreadentials();
        final LimitBadCreadentialsDaoAuthenticationProvider provider = new LimitBadCreadentialsDaoAuthenticationProvider();
        final UserDetailsService userDetailsService = getBeanOrNull(UserDetailsService.class);
        final PasswordEncoder passwordEncoder = getBeanOrNull(PasswordEncoder.class);
        final UserDetailsPasswordService passwordManager = getBeanOrNull(UserDetailsPasswordService.class);
        final BadCreadentialsService badCreadentialsService = getBeanOrNull(BadCreadentialsService.class);
        final AccountLockService accountLockService = getBeanOrNull(AccountLockService.class);
        provider.setUserDetailsService(userDetailsService);
        provider.setBadCreadentialsProperties(badCreadentials);
        if (null != passwordEncoder) {
            provider.setPasswordEncoder(passwordEncoder);
        }
        if (null != passwordManager) {
            provider.setUserDetailsPasswordService(passwordManager);
        }
        if (null != badCreadentialsService) {
            provider.setBadCreadentialsService(badCreadentialsService);
        }
        if (null != accountLockService) {
            provider.setAccountLockService(accountLockService);
        }
        provider.afterPropertiesSet();

        auth.authenticationProvider(provider);
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

        http.formLogin()
                .loginProcessingUrl("/login/username")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .rememberMe()
                .key("jarvis")
                .tokenRepository(persistentTokenRepository)
                .userDetailsService(userDetailsService)
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint);

        // 退出配置
        logout(http);

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
    }

    private <T> T getBeanOrNull(Class<T> type) {
        final String[] beanNames = getApplicationContext().getBeanNamesForType(type);
        if (beanNames.length != 1) {
            return null;
        }
        return getApplicationContext().getBean(beanNames[0], type);
    }

    private void logout(HttpSecurity http) throws Exception {
        final LogoutConfigurer<HttpSecurity> logout = http.logout();

        if (null != logoutHandlers && !logoutHandlers.isEmpty()) {
            logoutHandlers.forEach(h -> logout.addLogoutHandler(h));
        }

        logout.logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll();
    }
}
