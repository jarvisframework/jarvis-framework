package com.jarvis.platform.cloud.util;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.InetUtilsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(
    proxyBeanMethods = false
)
@ConditionalOnProperty(
    value = {"spring.cloud.util.enabled"},
    havingValue = "false"
)
@AutoConfigureOrder(-1)
@EnableConfigurationProperties({InetUtilsProperties.class})
public class ArchiveUtilAutoConfiguration {

    @Bean
    public InetUtils inetUtils(InetUtilsProperties properties) {
        return new InetUtils(properties);
    }
}
