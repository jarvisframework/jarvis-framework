package com.jarvis.framework.autoconfigure.security;

import com.jarvis.framework.security.validation.code.SmsCodeSender;
import com.jarvis.framework.security.validation.code.ValidateCodeGenerator;
import com.jarvis.framework.security.validation.code.ValidateCodeProcessor;
import com.jarvis.framework.security.validation.code.ValidateCodeProcessorHolder;
import com.jarvis.framework.security.validation.code.ValidateCodeStoreService;
import com.jarvis.framework.security.validation.code.config.ImageCodeProperties;
import com.jarvis.framework.security.validation.code.config.MobileCodeProperties;
import com.jarvis.framework.security.validation.code.config.ValidateCodeConfigService;
import com.jarvis.framework.security.validation.code.config.ValidateCodeProperties;
import com.jarvis.framework.security.validation.code.config.ValidateCodeSecurityConfig;
import com.jarvis.framework.security.validation.code.image.ImageCodeGenerator;
import com.jarvis.framework.security.validation.code.image.ImageCodeProcessor;
import com.jarvis.framework.security.validation.code.mobile.DefaultSmsSender;
import com.jarvis.framework.security.validation.code.mobile.MobileCodeGenerator;
import com.jarvis.framework.security.validation.code.mobile.MobileCodeProcessor;
import com.jarvis.framework.security.validation.code.web.ValidateCodeEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.util.Map;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月26日
 */

@Configuration(proxyBeanMethods = false)
//@ConditionalOnProperty(prefix = "spring.security.validate-code", name = "enabled", havingValue = "true")
@ConditionalOnClass({ ValidateCodeEndpoint.class })
@ComponentScan(basePackages = { "com.jarvis.framework.security.validation.code.web" })
public class ArchiveValidateCodeAutoConfiguration {

    @Autowired(required = false)
    private ValidateCodeStoreService validateCodeStoreService;

    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    ValidateCodeGenerator imageValidateCodeGenerator(ArchiveSecurityProperties securityProperties) {
        final ValidateCodeProperties validateCodeProperties = securityProperties.getValidateCode();
        ImageCodeProperties imageCodeProperties = null;
        if (null == validateCodeProperties || null == validateCodeProperties.getImage()) {
            imageCodeProperties = new ImageCodeProperties();
        } else {
            imageCodeProperties = validateCodeProperties.getImage();
        }
        return new ImageCodeGenerator(imageCodeProperties);
    }

    @Bean
    @ConditionalOnMissingBean(name = "mobileValidateCodeGenerator")
    ValidateCodeGenerator mobileValidateCodeGenerator(ArchiveSecurityProperties securityProperties) {
        final ValidateCodeProperties validateCodeProperties = securityProperties.getValidateCode();
        MobileCodeProperties mobileCodeProperties = null;
        if (null == validateCodeProperties || null == validateCodeProperties.getMobile()) {
            mobileCodeProperties = new MobileCodeProperties();
        } else {
            mobileCodeProperties = validateCodeProperties.getMobile();
        }
        return new MobileCodeGenerator(mobileCodeProperties);
    }

    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeProcessor")
    ValidateCodeProcessor imageValidateCodeProcessor(
            @Qualifier("imageValidateCodeGenerator") ValidateCodeGenerator imageCodeGenerator) {
        final ValidateCodeProcessor validateCodeProcessor = new ImageCodeProcessor(imageCodeGenerator);
        if (null != validateCodeStoreService) {
            validateCodeProcessor.setValidateCodeStoreService(validateCodeStoreService);
        }
        return validateCodeProcessor;
    }

    @Bean
    @ConditionalOnMissingBean(value = SmsCodeSender.class)
    SmsCodeSender smsCodeSender() {
        return new DefaultSmsSender();
    }

    @Bean
    @ConditionalOnMissingBean(name = "mobileValidateCodeProcessor")
    ValidateCodeProcessor mobileValidateCodeProcessor(SmsCodeSender smsCodeSender,
                                                      @Qualifier("mobileValidateCodeGenerator") ValidateCodeGenerator mobileCodeGenerator) {
        final ValidateCodeProcessor validateCodeProcessor = new MobileCodeProcessor(smsCodeSender,
                mobileCodeGenerator);
        if (null != validateCodeStoreService) {
            validateCodeProcessor.setValidateCodeStoreService(validateCodeStoreService);
        }
        return validateCodeProcessor;
    }

    @Bean
    ValidateCodeProcessorHolder validateCodeProcessorHolder(
            Map<String, ValidateCodeProcessor> validateCodeProcessors) {
        return new ValidateCodeProcessorHolder(validateCodeProcessors);
    }

    @Bean
    ValidateCodeSecurityConfig validateCodeSecurityConfigurer(
            AuthenticationFailureHandler authenticationFailureHandler,
            ValidateCodeProcessorHolder validateCodeProcessorHolder, ArchiveSecurityProperties securityProperties) {
        return new ValidateCodeSecurityConfig(authenticationFailureHandler, validateCodeProcessorHolder,
                securityProperties.getValidateCode());
    }

    @Bean
    ValidateCodeConfigService validateCodeConfigService(ArchiveSecurityProperties securityProperties) {
        return new ValidateCodeConfigService(securityProperties.getValidateCode());
    }

}
