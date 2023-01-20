package com.jarvis.framework.autoconfigure.security;

import com.jarvis.framework.security.authentication.LimitBadCreadentialsDaoAuthenticationProvider;
import com.jarvis.framework.security.authentication.config.BadCreadentialsProperties;
import com.jarvis.framework.security.constant.SecurityConstant;
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
import org.springframework.security.config.annotation.web.builders.WebSecurity.IgnoredRequestConfigurer;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
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
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Configuration(
    proxyBeanMethods = false
)
@ConditionalOnClass({WebSecurityConfigurerAdapter.class})
@ConditionalOnMissingClass({"com.gdda.archives.framework.oauth2.resource.server.config.Oauth2ResourceServerSecurityConfig"})
@EnableConfigurationProperties({ArchiveSecurityProperties.class})
@Import({ArchiveSecurityConfiguration.class, ArchiveRedisBadCreadentialsConfiguration.class, ArchiveSsoTokenConfiguration.class})
public class ArchiveSecurityAutoConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired(
        required = false
    )
    private List<LogoutHandler> logoutHandlers;
    @Autowired
    private PersistentTokenRepository persistentTokenRepository;
    @Autowired(
        required = false
    )
    private HttpSecurityProcessor httpSecurityProcessor;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired(
        required = false
    )
    private List<SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>> securityConfigurers;
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private ArchiveSecurityProperties securityProperties;

    protected void configure(AuthenticationManagerBuilder a) throws Exception {
        BadCreadentialsProperties var2 = a.securityProperties.getBadCreadentials();
        LimitBadCreadentialsDaoAuthenticationProvider var3 = new LimitBadCreadentialsDaoAuthenticationProvider();
        UserDetailsService var4 = (UserDetailsService)a.getBeanOrNull(UserDetailsService.class);
        PasswordEncoder var5 = (PasswordEncoder)a.getBeanOrNull(PasswordEncoder.class);
        UserDetailsPasswordService var6 = (UserDetailsPasswordService)a.getBeanOrNull(UserDetailsPasswordService.class);
        BadCreadentialsService var7 = (BadCreadentialsService)a.getBeanOrNull(BadCreadentialsService.class);
        var3.setUserDetailsService(var4);
        var3.setBadCreadentialsProperties(var2);
        if (null != var5) {
            var3.setPasswordEncoder(var5);
        }

        if (null != var6) {
            var3.setUserDetailsPasswordService(var6);
        }

        if (null != var7) {
            var3.setBadCreadentialsService(var7);
        }

        var3.afterPropertiesSet();
        a.authenticationProvider(var3);
    }

    public ArchiveSecurityAutoConfiguration() {
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

        ((HttpSecurity)((HttpSecurity)((HttpSecurity)((FormLoginConfigurer)((FormLoginConfigurer)((FormLoginConfigurer)a.formLogin().loginProcessingUrl(ArchiveSecurityConfiguration.oOoOOo("\t\\IWO^\tEUUT^G]C"))).successHandler(a.authenticationSuccessHandler)).failureHandler(a.authenticationFailureHandler)).and()).rememberMe().key(ArchiveValidateCodeAutoConfiguration.oOoOOo("P+R1X/T*")).tokenRepository(a.persistentTokenRepository).userDetailsService(a.userDetailsService).and()).csrf().disable()).exceptionHandling().accessDeniedHandler(a.accessDeniedHandler).authenticationEntryPoint(a.authenticationEntryPoint);
        a.logout(a);
        Optional.ofNullable(a.httpSecurityProcessor).ifPresent((ax) -> {
            ax.process(a);
        });
    }

    public void configure(WebSecurity a) throws Exception {
        ArrayList var2 = new ArrayList(a.securityProperties.getIgnoreUrls());
        Collections.addAll(var2, SecurityConstant.GLOBAL_IGNORE_URLS);
        IgnoredRequestConfigurer var10000 = a.ignoring();
        String[] var10002 = new String[var2.size()];
        boolean var10004 = true;
        var10000.antMatchers((String[])var2.toArray(var10002));
    }
}
