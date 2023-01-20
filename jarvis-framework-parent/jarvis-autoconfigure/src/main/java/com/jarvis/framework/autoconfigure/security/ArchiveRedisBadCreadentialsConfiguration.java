package com.jarvis.framework.autoconfigure.security;

import com.jarvis.framework.security.authentication.config.BadCreadentialsConfigService;
import com.jarvis.framework.security.service.BadCreadentialsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@ConditionalOnClass({RedisTemplate.class})
public class ArchiveRedisBadCreadentialsConfiguration {
    @Bean
    BadCreadentialsConfigService badCreadentialsConfigService(ArchiveSecurityProperties a) {
        return new BadCreadentialsConfigService(a.getBadCreadentials());
    }

    @Bean
    @ConditionalOnMissingBean({BadCreadentialsService.class})
    BadCreadentialsService redisBadCreadentialsService(RedisConnectionFactory a, ArchiveSecurityProperties a) {
        return new RedisBadCreadentialsService(a, a.getBadCreadentials());
    }

    public ArchiveRedisBadCreadentialsConfiguration() {
    }
}
