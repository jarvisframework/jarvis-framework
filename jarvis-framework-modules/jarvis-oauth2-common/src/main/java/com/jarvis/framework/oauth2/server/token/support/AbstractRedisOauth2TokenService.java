package com.jarvis.framework.oauth2.server.token.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import comjarvis.framework.redis.builder.StringObjectRedisTemplateBuilder;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.core.Authentication;
import org.springframework.security.jackson2.SecurityJackson2Modules;

public abstract class AbstractRedisOauth2TokenService {

    protected abstract RedisTemplate<String, ?> getRedisTemplate();

    protected abstract int getAccessTokenTimeout();

    protected byte[] serializeKey(String prefix, String body) {
        return this.serializeKey(prefix.concat(body));
    }

    protected byte[] serializeKey(String key) {
        RedisSerializer keySerializer = StringRedisSerializer.UTF_8;
        return keySerializer.serialize(key);
    }

    protected byte[] serializeValue(Object value) {
        RedisSerializer keySerializer = this.getRedisTemplate().getValueSerializer();
        return keySerializer.serialize(value);
    }

    protected RedisTemplate<String, Object> initRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        ObjectMapper objectMapper = StringObjectRedisTemplateBuilder.jsonRedisSerializerMapper();
        objectMapper.registerModules(SecurityJackson2Modules.getModules(this.getClass().getClassLoader()));
        return StringObjectRedisTemplateBuilder.build(redisConnectionFactory, objectMapper);
    }

    protected Authentication toAuthentication(Object obj) {
        return (Authentication)obj;
    }
}
