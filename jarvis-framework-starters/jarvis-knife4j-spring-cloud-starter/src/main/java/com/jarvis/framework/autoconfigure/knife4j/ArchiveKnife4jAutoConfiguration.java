package com.jarvis.framework.autoconfigure.knife4j;

import com.jarvis.framework.autoconfigure.springfox.ArchiveSpringfoxProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.oas.mappers.ServiceModelToOpenApiMapper;

@Configuration(
    proxyBeanMethods = false
)
@ConditionalOnProperty(
    value = {"springfox.documentation.enabled"},
    havingValue = "true",
    matchIfMissing = true
)
@ConditionalOnWebApplication
@EnableConfigurationProperties({ArchiveSpringfoxProperties.class})
public class ArchiveKnife4jAutoConfiguration {
    public ArchiveKnife4jAutoConfiguration() {
    }

    @Bean
    @Primary
    ServiceModelToOpenApiMapper archiveServiceModelToOpenApiMapperImpl() {
        return new ArchiveServiceModelToOpenApiMapperImpl();
    }
}
