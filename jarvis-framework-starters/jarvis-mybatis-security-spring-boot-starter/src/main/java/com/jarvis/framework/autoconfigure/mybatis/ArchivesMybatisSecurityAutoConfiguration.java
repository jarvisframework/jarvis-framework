package com.jarvis.framework.autoconfigure.mybatis;

import com.jarvis.framework.mybatis.handler.EntityAutoFillingHandler;
import com.jarvis.framework.mybatis.handler.support.EntityAutoFillingDefaultHandler;
import com.jarvis.framework.mybatis.handler.support.EntityFillingSupportHandler;
import com.jarvis.framework.mybatis.handler.support.LongIdDynamicSupportHandler;
import com.jarvis.framework.mybatis.handler.support.LongIdSimpleSupportHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.List;

@Configuration(
    proxyBeanMethods = false
)
public class ArchivesMybatisSecurityAutoConfiguration {
    public ArchivesMybatisSecurityAutoConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean({EntityAutoFillingHandler.class})
    EntityAutoFillingHandler entityAutoFillingDefaultHandler(List<EntityFillingSupportHandler> supportHandlers) {
        supportHandlers.sort(AnnotationAwareOrderComparator.INSTANCE);
        return new EntityAutoFillingDefaultHandler(supportHandlers);
    }

    @Bean
    EntityFillingSupportHandler longIdDynamicSupportHandler() {
        return new LongIdDynamicSupportHandler();
    }

    @Bean
    EntityFillingSupportHandler longIdSimpleSupportHandler() {
        return new LongIdSimpleSupportHandler();
    }
}
