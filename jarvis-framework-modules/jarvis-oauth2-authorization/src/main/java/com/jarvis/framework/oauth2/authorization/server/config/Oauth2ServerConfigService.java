package com.jarvis.framework.oauth2.authorization.server.config;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

public class Oauth2ServerConfigService extends AbstractRedisOauth2TokenService {
    private final Oauth2ServerProperties properties;
    private final RedisTemplate<String, Object> redisTemplate;

    public Oauth2ServerConfigService(RedisConnectionFactory redisConnectionFactory, Oauth2ServerProperties properties) {
        this.properties = properties;
        this.redisTemplate = this.initRedisTemplate(redisConnectionFactory);
    }

    public void setAccessTokenTimeout(int accessTokenTimeout) {
        Assert.isTrue(accessTokenTimeout > 0, "过期时间必须大于0");
        this.properties.setAccessTokenTimeout(accessTokenTimeout);
        this.redisTemplate.opsForValue().set("access_token:timeout", this.getAccessTokenTimeout());
    }

    public void setConcurrentAccess(boolean concurrentAccess) {
        this.properties.setConcurrentAccess(concurrentAccess);
    }

    protected RedisTemplate<String, ?> getRedisTemplate() {
        return this.redisTemplate;
    }

    protected int getAccessTokenTimeout() {
        return this.properties.getAccessTokenTimeout() * 60;
    }
}
