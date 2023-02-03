package com.jarvis.framework.autoconfigure.security;

import com.jarvis.framework.autoconfigure.security.service.RedisBadCreadentialsService;
import com.jarvis.framework.security.authentication.config.BadCreadentialsConfigService;
import com.jarvis.framework.security.service.BadCreadentialsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月27日
 */
//@ConditionalOnBean(RedisTemplate.class)
@ConditionalOnClass(RedisTemplate.class)
//@ConditionalOnProperty(prefix = "spring.security.bad-creadentials", name = "enabled", havingValue = "true")
public class ArchiveRedisBadCreadentialsConfiguration {

    @Bean
    @ConditionalOnMissingBean(BadCreadentialsService.class)
    BadCreadentialsService redisBadCreadentialsService(RedisConnectionFactory connectionFactory,
                                                       ArchiveSecurityProperties properties) {
        return new RedisBadCreadentialsService(connectionFactory, properties.getBadCreadentials());
    }

    @Bean
    BadCreadentialsConfigService badCreadentialsConfigService(ArchiveSecurityProperties properties) {
        return new BadCreadentialsConfigService(properties.getBadCreadentials());
    }

}
