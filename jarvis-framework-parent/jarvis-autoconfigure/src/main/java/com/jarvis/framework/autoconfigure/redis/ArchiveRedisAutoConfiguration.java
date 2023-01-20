package com.jarvis.framework.autoconfigure.redis;

import com.jarvis.framework.autoconfigure.mybatis.DruidExtendProperties;
import comjarvis.framework.redis.builder.StringObjectRedisTemplateBuilder;
import comjarvis.framework.redis.core.StringObjectRedisTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration(
    proxyBeanMethods = false
)
@ConditionalOnClass({RedisOperations.class, StringObjectRedisTemplate.class})
@EnableConfigurationProperties({RedisProperties.class})
@AutoConfigureAfter({RedisAutoConfiguration.class})
@EnableCaching
public class ArchiveRedisAutoConfiguration {
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory a) {
        RedisCacheConfiguration var2 = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ZERO).computePrefixWith((ax) -> {
            return (new StringBuilder()).insert(0, ax).append(DruidExtendProperties.oOoOOo("\u000b")).toString();
        }).serializeKeysWith(SerializationPair.fromSerializer(new StringRedisSerializer())).serializeValuesWith(SerializationPair.fromSerializer(StringObjectRedisTemplateBuilder.genericJackson2JsonRedisSerializer())).disableCachingNullValues();
        return RedisCacheManagerBuilder.fromConnectionFactory(a).cacheDefaults(var2).transactionAware().build();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnSingleCandidate(RedisConnectionFactory.class)
    public StringObjectRedisTemplate stringObjectRedisTemplate(RedisConnectionFactory a) {
        return new StringObjectRedisTemplate(a);
    }

    public ArchiveRedisAutoConfiguration() {
    }
}
