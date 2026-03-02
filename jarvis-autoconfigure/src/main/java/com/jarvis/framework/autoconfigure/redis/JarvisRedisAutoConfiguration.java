package com.jarvis.framework.autoconfigure.redis;

import com.jarvis.framework.redis.builder.StringObjectRedisTemplateBuilder;
import com.jarvis.framework.redis.cache.CacheMessage;
import com.jarvis.framework.redis.cache.MultiLevelCacheManager;
import com.jarvis.framework.redis.cache.MultiLevelCacheProperties;
import com.jarvis.framework.redis.core.StringObjectRedisTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年11月10日
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ RedisOperations.class, StringObjectRedisTemplate.class })
@EnableConfigurationProperties({RedisProperties.class, MultiLevelCacheProperties.class})
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableCaching
public class JarvisRedisAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnSingleCandidate(RedisConnectionFactory.class)
    public StringObjectRedisTemplate stringObjectRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StringObjectRedisTemplate(redisConnectionFactory);
    }

    @Bean
    @ConditionalOnMissingBean
    public CacheManager cacheManager(RedisConnectionFactory lettuceConnectionFactory,
                                     MultiLevelCacheProperties multiLevelCacheProperties,
                                     StringObjectRedisTemplate stringObjectRedisTemplate) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ZERO)
                //变双冒号为单冒号
                .computePrefixWith(name -> name + ":")
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(StringObjectRedisTemplateBuilder.genericJackson2JsonRedisSerializer()))
                .disableCachingNullValues();

        if (multiLevelCacheProperties.isEnabled()) {
            if (multiLevelCacheProperties.getRedis().getDefaultExpiration() > 0) {
                config = config.entryTtl(Duration.ofSeconds(multiLevelCacheProperties.getRedis().getDefaultExpiration()));
            }

            RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder
                    .fromConnectionFactory(lettuceConnectionFactory)
                    .cacheDefaults(config)
                    .transactionAware();

            Map<String, Long> expires = multiLevelCacheProperties.getRedis().getExpires();
            if (expires != null && !expires.isEmpty()) {
                Map<String, RedisCacheConfiguration> initialCacheConfigurations = new HashMap<>();
                RedisCacheConfiguration finalConfig = config;
                expires.forEach((k, v) -> {
                    initialCacheConfigurations.put(k, finalConfig.entryTtl(Duration.ofSeconds(v)));
                });
                builder.withInitialCacheConfigurations(initialCacheConfigurations);
            }

            RedisCacheManager redisCacheManager = builder.build();
            return new MultiLevelCacheManager(redisCacheManager, multiLevelCacheProperties, stringObjectRedisTemplate);
        }

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(lettuceConnectionFactory)
                .cacheDefaults(config)
                .transactionAware()
                .build();
    }

    @Bean
    @ConditionalOnProperty(name = "jarvis.cache.multi.enabled", havingValue = "true")
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory,
                                                                       MultiLevelCacheManager multiLevelCacheManager) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);

        MessageListenerAdapter adapter = new MessageListenerAdapter(new Object() {
            @SuppressWarnings("unused")
            public void handleMessage(CacheMessage message) {
                if (message.getSourceId() == null || !message.getSourceId().equals(multiLevelCacheManager.getSourceId())) {
                    multiLevelCacheManager.clearLocal(message.getCacheName(), message.getKey());
                }
            }
        }, "handleMessage");
        adapter.setSerializer(StringObjectRedisTemplateBuilder.genericJackson2JsonRedisSerializer());
        adapter.afterPropertiesSet();

        container.addMessageListener(adapter, new PatternTopic(MultiLevelCacheManager.DEFAULT_TOPIC));
        return container;
    }
}
