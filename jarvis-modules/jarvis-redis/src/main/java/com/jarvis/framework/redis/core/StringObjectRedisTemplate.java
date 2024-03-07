package com.jarvis.framework.redis.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarvis.framework.redis.builder.StringObjectRedisTemplateBuilder;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年5月12日
 */
public class StringObjectRedisTemplate extends RedisTemplate<String, Object> {

    public StringObjectRedisTemplate() {
        setKeySerializer(RedisSerializer.string());
        setHashKeySerializer(RedisSerializer.string());
    }

    public StringObjectRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        this();
        final GenericJackson2JsonRedisSerializer valueSerializer = StringObjectRedisTemplateBuilder
                .genericJackson2JsonRedisSerializer();
        setValueSerializer(valueSerializer);
        setHashKeySerializer(valueSerializer);
        setConnectionFactory(redisConnectionFactory);
        afterPropertiesSet();
    }

    public StringObjectRedisTemplate(RedisConnectionFactory redisConnectionFactory, ObjectMapper objectMapper) {
        this();
        final GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);
        setValueSerializer(valueSerializer);
        setHashKeySerializer(valueSerializer);
        setConnectionFactory(redisConnectionFactory);
        afterPropertiesSet();
    }

}
