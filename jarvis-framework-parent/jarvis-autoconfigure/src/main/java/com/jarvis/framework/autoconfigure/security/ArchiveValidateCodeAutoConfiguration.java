package com.jarvis.framework.autoconfigure.security;

import com.jarvis.framework.security.validation.code.SmsCodeSender;
import com.jarvis.framework.security.validation.code.ValidateCodeGenerator;
import com.jarvis.framework.security.validation.code.ValidateCodeProcessor;
import com.jarvis.framework.security.validation.code.ValidateCodeProcessorHolder;
import com.jarvis.framework.security.validation.code.ValidateCodeStoreService;
import com.jarvis.framework.security.validation.code.config.ImageCodeProperties;
import com.jarvis.framework.security.validation.code.config.MobileCodeProperties;
import com.jarvis.framework.security.validation.code.config.ValidateCodeConfigService;
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

@Configuration(
    proxyBeanMethods = false
)
@ConditionalOnClass({ValidateCodeEndpoint.class})
@ComponentScan(
    basePackages = {"com.gdda.archives.framework.security.validation.code.web"}
)
public class ArchiveValidateCodeAutoConfiguration {
    @Autowired(
        required = false
    )
    private ValidateCodeStoreService validateCodeStoreService;

    @Bean
    ValidateCodeSecurityConfig validateCodeSecurityConfigurer(AuthenticationFailureHandler a, ValidateCodeProcessorHolder a, ArchiveSecurityProperties a) {
        return new ValidateCodeSecurityConfig(a, a, a.getValidateCode());
    }

    @Bean
    @ConditionalOnMissingBean(
        name = {"imageValidateCodeProcessor"}
    )
    ValidateCodeProcessor imageValidateCodeProcessor(@Qualifier("imageValidateCodeGenerator") ValidateCodeGenerator a) {
        ValidateCodeGenerator a = new ImageCodeProcessor(a);
        if (null != this.validateCodeStoreService) {
            a.setValidateCodeStoreService(this.validateCodeStoreService);
        }

        return a;
    }

    @Bean
    ValidateCodeProcessorHolder validateCodeProcessorHolder(Map<String, ValidateCodeProcessor> a) {
        return new ValidateCodeProcessorHolder(a);
    }

    @Bean
    @ConditionalOnMissingBean(
        name = {"imageValidateCodeGenerator"}
    )
    ValidateCodeGenerator imageValidateCodeGenerator(ArchiveSecurityProperties a) {
        ArchiveSecurityProperties a = a.getValidateCode();
        ImageCodeProperties var2 = null;
        if (null != a && null != a.getImage()) {
            var2 = a.getImage();
        } else {
            var2 = new ImageCodeProperties();
        }

        return new ImageCodeGenerator(var2);
    }

    @Bean
    @ConditionalOnMissingBean({SmsCodeSender.class})
    SmsCodeSender smsCodeSender() {
        return new DefaultSmsSender();
    }

    @Bean
    @ConditionalOnMissingBean(
        name = {"mobileValidateCodeProcessor"}
    )
    ValidateCodeProcessor mobileValidateCodeProcessor(SmsCodeSender smsCodeSender, @Qualifier("mobileValidateCodeGenerator") ValidateCodeGenerator a) {
        SmsCodeSender smsCodeSender = new MobileCodeProcessor(smsCodeSender, a);
        if (null != this.validateCodeStoreService) {
            smsCodeSender.setValidateCodeStoreService(this.validateCodeStoreService);
        }

        return smsCodeSender;
    }

    @Bean
    ValidateCodeConfigService validateCodeConfigService(ArchiveSecurityProperties a) {
        return new ValidateCodeConfigService(a.getValidateCode());
    }

    public static String oOoOOo(String a) {
        int var10000 = 5 << 4 ^ 2 << 2 ^ 1;
        int var10001 = 4 << 4;
        int var10002 = (3 ^ 5) << 3 ^ 1;
        int var10003 = (a = (String)a).length();
        char[] var10004 = new char[var10003];
        boolean var10006 = true;
        int var5 = var10003 - 1;
        var10003 = var10002;
        int var3;
        var10002 = var3 = var5;
        char[] var1 = var10004;
        int var4 = var10003;
        var10001 = var10000;
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
    @ConditionalOnMissingBean(
        name = {"mobileValidateCodeGenerator"}
    )
    ValidateCodeGenerator mobileValidateCodeGenerator(ArchiveSecurityProperties a) {
        ArchiveSecurityProperties a = a.getValidateCode();
        MobileCodeProperties var2 = null;
        if (null != a && null != a.getMobile()) {
            var2 = a.getMobile();
        } else {
            var2 = new MobileCodeProperties();
        }

        return new MobileCodeGenerator(var2);
    }

    public ArchiveValidateCodeAutoConfiguration() {
    }
}
