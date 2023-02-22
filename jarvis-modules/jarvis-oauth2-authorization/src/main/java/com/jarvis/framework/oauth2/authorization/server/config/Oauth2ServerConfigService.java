package com.jarvis.framework.oauth2.authorization.server.config;

import com.jarvis.framework.oauth2.server.token.constant.RedisTokenConstant;
import com.jarvis.framework.oauth2.server.token.support.AbstractRedisOauth2TokenService;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年7月28日
 */
public class Oauth2ServerConfigService extends AbstractRedisOauth2TokenService {

    private final Oauth2ServerProperties properties;

    private final RedisTemplate<String, Object> redisTemplate;

    public Oauth2ServerConfigService(RedisConnectionFactory redisConnectionFactory,
                                     Oauth2ServerProperties properties) {
        this.properties = properties;
        redisTemplate = initRedisTemplate(redisConnectionFactory);
    }

    /**
     * 过期时间（单位为分钟）
     *
     * @param accessTokenTimeout the accessTokenTimeout to set
     */
    public void setAccessTokenTimeout(int accessTokenTimeout) {
        Assert.isTrue(accessTokenTimeout > 0, "过期时间必须大于0");
        this.properties.setAccessTokenTimeout(accessTokenTimeout);
        redisTemplate.opsForValue().set(RedisTokenConstant.ACCESS_TOKEN_TIMEOUT, getAccessTokenTimeout());

    }

    /**
     * 是否允许并发登录
     *
     * @param concurrentAccess true 表示可以并发登录
     */
    public void setConcurrentAccess(boolean concurrentAccess) {
        this.properties.setConcurrentAccess(concurrentAccess);
    }

    /**
     *
     * @see com.jarvis.framework.oauth2.server.token.support.AbstractRedisOauth2TokenService#getRedisTemplate()
     */
    @Override
    protected RedisTemplate<String, ?> getRedisTemplate() {
        return redisTemplate;
    }

    /**
     *
     * @see com.jarvis.framework.oauth2.server.token.support.AbstractRedisOauth2TokenService#getAccessTokenTimeout()
     */
    @Override
    protected int getAccessTokenTimeout() {
        return properties.getAccessTokenTimeout() * 60;
    }

}
