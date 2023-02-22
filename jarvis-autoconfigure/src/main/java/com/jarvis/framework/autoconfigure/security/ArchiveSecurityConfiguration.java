package com.jarvis.framework.autoconfigure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarvis.framework.security.authentication.JsonAccessDeniedHandler;
import com.jarvis.framework.security.authentication.JsonAuthenctiationFailureHandler;
import com.jarvis.framework.security.authentication.JsonAuthenticationEntryPoint;
import com.jarvis.framework.security.authentication.JsonAuthenticationSuccessHandler;
import com.jarvis.framework.security.authentication.JsonLogoutSuccessHandler;
import com.jarvis.framework.security.model.SecurityUser;
import com.jarvis.framework.security.password.DelegatePasswordEncoder;
import com.jarvis.framework.security.password.crypto.SM3PasswordEncoder;
import com.jarvis.framework.webmvc.web.exception.handler.ExceptionProcessor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
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
import java.util.Map;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月26日
 */
@SuppressWarnings("deprecation")
public class ArchiveSecurityConfiguration {

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
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    LogoutSuccessHandler logoutSuccessHandler(ObjectMapper objectMapper) {
        return new JsonLogoutSuccessHandler(objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(AccessDeniedHandler.class)
    AccessDeniedHandler accessDeniedHandler(ObjectMapper objectMapper) {
        return new JsonAccessDeniedHandler(objectMapper);
    }

    /*@Bean
    IdcardAuthenticationSecurityConfig IdcardAuthenticationSecurityConfig(
        AuthenticationSuccessHandler authenticationSuccessHandler,
        AuthenticationFailureHandler authenticationFailureHandler) {
        return new IdcardAuthenticationSecurityConfig(authenticationSuccessHandler, authenticationFailureHandler);
    }*/

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                if (!"superadmin".equals(username)) {
                    throw new UsernameNotFoundException("用户名或密码错误");
                }
                final SecurityUser user = new SecurityUser();
                user.setId("1");
                user.setUsername("superadmin");
                user.setShowName("superadmin");
                user.setPassword(passwordEncoder.encode("000000"));
                user.addAuthorities(new SimpleGrantedAuthority("superadmin"));
                return user;
            }
        };
    }

    @Bean
    DelegatePasswordEncoder sm3PasswordEncoder() {
        return new DelegatePasswordEncoder() {

            @Override
            public String encoderId() {
                return "SM3";
            }

            @Override
            public PasswordEncoder passwordEncoder() {
                return new SM3PasswordEncoder();
            }

        };
    }

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    PasswordEncoder passwordEncoder(ArchiveSecurityProperties properties,
                                    ObjectProvider<List<DelegatePasswordEncoder>> passwordEncoderProvider) {
        final String encodingId = null == properties.getPasswordEncoder() ? "MD5" : properties.getPasswordEncoder();
        final Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new BCryptPasswordEncoder());
        encoders.put("ldap", new org.springframework.security.crypto.password.LdapShaPasswordEncoder());
        encoders.put("MD4", new org.springframework.security.crypto.password.Md4PasswordEncoder());
        encoders.put("MD5", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("MD5"));
        encoders.put("noop", org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("SHA-1", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-1"));
        encoders.put("SHA-256",
                new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-256"));
        encoders.put("sha256", new org.springframework.security.crypto.password.StandardPasswordEncoder());
        encoders.put("argon2", new Argon2PasswordEncoder());
        final List<DelegatePasswordEncoder> passwordEncoders = passwordEncoderProvider.getIfAvailable();
        if (null != passwordEncoders) {
            passwordEncoders.forEach(pe -> {
                encoders.put(pe.encoderId(), pe.passwordEncoder());
            });
        }
        return new DelegatingPasswordEncoder(encodingId, encoders);
    }

    @Bean
    @ConditionalOnMissingBean(PersistentTokenRepository.class)
    PersistentTokenRepository persistentTokenRepository() {
        final PersistentTokenRepository tokenRepository = new InMemoryTokenRepositoryImpl();
        return tokenRepository;
    }

    @Bean
    ExceptionProcessor validateCodeExceptionProcessor() {
        return new ValidateCodeExceptionProcessor();
    }

    /*public static void main(String[] args) {
        System.out.println(
            new ArchiveSecurityConfiguration().passwordEncoder(new ArchiveSecurityProperties()).encode("000000"));
    }*/
}
