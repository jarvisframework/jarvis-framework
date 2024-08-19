package com.jarvis.framework.autoconfigure.bizlog;

import com.jarvis.framework.bizlog.BizLogEventListener;
import com.jarvis.framework.bizlog.BizLogModel;
import com.jarvis.framework.bizlog.BizLogService;
import com.jarvis.framework.bizlog.aspect.BizLoggerAspect;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年7月20日
 */
@EnableAsync
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(value = BizLogService.class)
@ConditionalOnWebApplication
@EnableConfigurationProperties(JarvisBizLoggerProperties.class)
public class JarvisBizLogAutoConfiguration {

    @Bean
    public BizLogEventListener bizLogEventListener(BizLogService bizLogService) {
        return new BizLogEventListener(bizLogService);
    }

    @Bean
    public BizLoggerAspect bizLoggerAspect(ApplicationEventPublisher publisher,
                                           JarvisBizLoggerProperties properties, ObjectProvider<BizLogModel> modelServiceProvider) {
        return new BizLoggerAspect(properties.getLevel(), publisher, modelServiceProvider.getIfAvailable(),
                properties.getDelimiter());
    }
}
