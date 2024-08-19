package com.jarvis.framework.autoconfigure.knife4j;

import com.jarvis.framework.autoconfigure.springfox.JarvisSpringfoxProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.oas.mappers.ServiceModelToOpenApiMapper;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年8月2日
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(value = "springfox.documentation.enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnWebApplication
@EnableConfigurationProperties(JarvisSpringfoxProperties.class)
public class JarvisKnife4jAutoConfiguration {

    @Bean
    @Primary
    ServiceModelToOpenApiMapper jarvisServiceModelToOpenApiMapperImpl() {
        return new JarvisServiceModelToOpenApiMapperImpl();
    }
}
