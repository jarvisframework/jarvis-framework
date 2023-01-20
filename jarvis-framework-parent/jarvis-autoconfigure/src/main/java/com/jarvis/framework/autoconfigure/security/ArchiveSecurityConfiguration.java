package com.jarvis.framework.autoconfigure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarvis.framework.webmvc.web.exception.handler.ExceptionProcessor;
import com.jarvis.framework.autoconfigure.mybatis.DruidExtendProperties;
import com.jarvis.framework.security.authentication.JsonAccessDeniedHandler;
import com.jarvis.framework.security.authentication.JsonAuthenctiationFailureHandler;
import com.jarvis.framework.security.authentication.JsonAuthenticationEntryPoint;
import com.jarvis.framework.security.authentication.JsonAuthenticationSuccessHandler;
import com.jarvis.framework.security.authentication.JsonLogoutSuccessHandler;
import com.jarvis.framework.security.model.SecurityUser;
import com.jarvis.framework.security.password.DelegatePasswordEncoder;
import com.jarvis.framework.security.password.crypto.SM3PasswordEncoder;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.Md4PasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import java.util.HashMap;
import java.util.List;

public class ArchiveSecurityConfiguration {
    @Bean
    @ConditionalOnMissingBean({AccessDeniedHandler.class})
    AccessDeniedHandler accessDeniedHandler(ObjectMapper a) {
        return new JsonAccessDeniedHandler(a);
    }

    public static String oOoOOo(String a) {
        int var10000 = 5 << 4 ^ 4 << 1;
        int var10001 = 4 << 3 ^ 3 ^ 5;
        int var10002 = (3 ^ 5) << 3;
        int var10003 = (a = (String)a).length();
        char[] var10004 = new char[var10003];
        boolean var10006 = true;
        int var5 = var10003 - 1;
        var10003 = var10002;
        int var3;
        var10002 = var3 = var5;
        char[] var1 = var10004;
        int var4 = var10003;
        var10000 = var10002;

        for(int var2 = var10001; var10000 >= 0; var10000 = var3) {
            var10001 = var3;
            char var6 = a.charAt(var3);
            --var3;
            var1[var10001] = (char)(var6 ^ var2);
            if (var3 < 0) {
                break;
            }

            var10002 = var3--;
            var1[var10002] = (char)(a.charAt(var10002) ^ var4);
        }

        return new String(var1);
    }

    @Bean
    @ConditionalOnMissingBean({PasswordEncoder.class})
    PasswordEncoder passwordEncoder(ArchiveSecurityProperties a, ObjectProvider<List<DelegatePasswordEncoder>> a) {
        String var3 = null == a.getPasswordEncoder() ? "MD5" : a.getPasswordEncoder();
        ArchiveSecurityProperties a = new HashMap();
        a.put(var3, new BCryptPasswordEncoder());
        a.put(oOoOOo("\\BQV"), new LdapShaPasswordEncoder());
        a.put(oOoOOo("kt\u0012"), new Md4PasswordEncoder());
        a.put("MD5", new MessageDigestPasswordEncoder("MD5"));
        a.put(oOoOOo("^I_V"), NoOpPasswordEncoder.getInstance());
        a.put(oOoOOo("@D[BV\u0014"), new Pbkdf2PasswordEncoder());
        a.put(oOoOOo("CEB_@R"), new SCryptPasswordEncoder());
        a.put(oOoOOo("uxg\u001d\u0017"), new MessageDigestPasswordEncoder(oOoOOo("uxg\u001d\u0017")));
        a.put(oOoOOo("uxg\u001d\u0014\u0005\u0010"), new MessageDigestPasswordEncoder(oOoOOo("uxg\u001d\u0014\u0005\u0010")));
        a.put(oOoOOo("CNQ\u0014\u0005\u0010"), new StandardPasswordEncoder());
        a.put(oOoOOo("QTWI^\u0014"), new Argon2PasswordEncoder());
        ObjectProvider a = (List)a.getIfAvailable();
        if (null != a) {
            a.forEach((ax) -> {
                a.put(ax.encoderId(), ax.passwordEncoder());
            });
        }

        return new DelegatingPasswordEncoder(var3, a);
    }

    @Bean
    @ConditionalOnMissingBean({UserDetailsService.class})
    UserDetailsService userDetailsService(final PasswordEncoder a) {
        return new UserDetailsService() {
            public UserDetails loadUserByUsername(String axx) throws UsernameNotFoundException {
                if (!ArchiveValidateCodeAutoConfiguration.oOoOOo("B,A<C8U4X7").equals(axx)) {
                    throw new UsernameNotFoundException(DruidExtendProperties.oOoOOo("畼戆呙戧宒砰镍诞"));
                } else {
                    SecurityUser var2 = new SecurityUser();
                    var2.setId(ArchiveValidateCodeAutoConfiguration.oOoOOo("h"));
                    var2.setUsername(DruidExtendProperties.oOoOOo("'D$T&P0\\=_"));
                    var2.setShowName(ArchiveValidateCodeAutoConfiguration.oOoOOo("B,A<C8U4X7"));
                    var2.setPassword(a.encode(DruidExtendProperties.oOoOOo("d\u0001d\u0001d\u0001")));
                    GrantedAuthority[] var10001 = new GrantedAuthority[1];
                    boolean var10003 = true;
                    boolean var10006 = false;
                    var10001[0] = new SimpleGrantedAuthority(ArchiveValidateCodeAutoConfiguration.oOoOOo("B,A<C8U4X7"));
                    var2.addAuthorities(var10001);
                    return var2;
                }
            }
        };
    }

    public ArchiveSecurityConfiguration() {
    }

    @Bean
    ExceptionProcessor validateCodeExceptionProcessor() {
        return new ValidateCodeExceptionProcessor();
    }

    @Bean
    @ConditionalOnMissingBean({AuthenticationFailureHandler.class})
    AuthenticationFailureHandler authenticationFailureHandler(ObjectMapper a) {
        return new JsonAuthenctiationFailureHandler(a);
    }

    @Bean
    @ConditionalOnMissingBean({AuthenticationSuccessHandler.class})
    AuthenticationSuccessHandler authenticationSuccessHandler(ObjectMapper a) {
        return new JsonAuthenticationSuccessHandler(a);
    }

    @Bean
    DelegatePasswordEncoder sm3PasswordEncoder() {
        return new DelegatePasswordEncoder() {
            public String encoderId() {
                return ArchiveSecurityConfiguration.oOoOOo("u}\u0015");
            }

            public PasswordEncoder passwordEncoder() {
                return new SM3PasswordEncoder();
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean({AuthenticationEntryPoint.class})
    AuthenticationEntryPoint authenticationEntryPoint(ObjectMapper a) {
        return new JsonAuthenticationEntryPoint(a);
    }

    @Bean
    @ConditionalOnMissingBean({PersistentTokenRepository.class})
    PersistentTokenRepository persistentTokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean({LogoutSuccessHandler.class})
    LogoutSuccessHandler logoutSuccessHandler(ObjectMapper a) {
        return new JsonLogoutSuccessHandler(a);
    }
}
