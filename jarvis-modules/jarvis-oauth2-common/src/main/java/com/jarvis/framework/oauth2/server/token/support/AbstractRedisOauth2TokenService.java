package com.jarvis.framework.oauth2.server.token.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import comjarvis.framework.redis.builder.StringObjectRedisTemplateBuilder;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.core.Authentication;
import org.springframework.security.jackson2.SecurityJackson2Modules;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月23日
 */
public abstract class AbstractRedisOauth2TokenService {

    protected abstract RedisTemplate<String, ?> getRedisTemplate();

    /**
     * 过期时间，单位为秒
     *
     * @return int
     */
    protected abstract int getAccessTokenTimeout();

    protected byte[] serializeKey(String prefix, String body) {
        return serializeKey(prefix.concat(body));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected byte[] serializeKey(String key) {
        final RedisSerializer keySerializer = StringRedisSerializer.UTF_8;
        return keySerializer.serialize(key);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected byte[] serializeValue(Object value) {
        final RedisSerializer keySerializer = getRedisTemplate().getValueSerializer();
        return keySerializer.serialize(value);
    }

    protected RedisTemplate<String, Object> initRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        final ObjectMapper objectMapper = StringObjectRedisTemplateBuilder.jsonRedisSerializerMapper();
        objectMapper.registerModules(SecurityJackson2Modules.getModules(getClass().getClassLoader()));
        return StringObjectRedisTemplateBuilder.build(redisConnectionFactory, objectMapper);
    }

    protected Authentication toAuthentication(Object obj) {
        return (Authentication) obj;
    }

}
