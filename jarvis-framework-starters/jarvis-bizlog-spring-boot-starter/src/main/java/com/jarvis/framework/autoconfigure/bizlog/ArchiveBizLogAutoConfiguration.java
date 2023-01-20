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

@EnableAsync
@Configuration(
    proxyBeanMethods = false
)
@ConditionalOnBean({BizLogService.class})
@ConditionalOnWebApplication
@EnableConfigurationProperties({ArchiveBizLoggerProperties.class})
public class ArchiveBizLogAutoConfiguration {

    @Bean
    public BizLogEventListener bizLogEventListener(BizLogService bizLogService) {
        return new BizLogEventListener(bizLogService);
    }

    @Bean
    public BizLoggerAspect bizLoggerAspect(ApplicationEventPublisher publisher, ArchiveBizLoggerProperties properties, ObjectProvider<BizLogModel> modelServiceProvider) {
        return new BizLoggerAspect(properties.getLevel(), publisher, (BizLogModel)modelServiceProvider.getIfAvailable(), properties.getDelimiter());
    }
}
