package com.jarvis.framework.oauth2.server.token.support;

import com.jarvis.framework.oauth2.server.token.Oauth2TokenObtainService;
import com.jarvis.framework.oauth2.server.token.constant.RedisTokenConstant;
import com.jarvis.framework.security.model.SecurityUser;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月23日
 */
public class RedisOauth2TokenObtainService extends AbstractRedisOauth2TokenService implements Oauth2TokenObtainService {

    private final RedisTemplate<String, Object> redisTemplate;

    private final int defaultAccessTokenTimeout = 6 * 60 * 60;

    public RedisOauth2TokenObtainService(RedisConnectionFactory redisConnectionFactory) {
        this.redisTemplate = initRedisTemplate(redisConnectionFactory);
    }

    /**
     *
     * @see com.jarvis.framework.oauth2.server.token.Oauth2TokenObtainService#getByAccessToken(java.lang.String)
     */
    @Override
    public Authentication getByAccessToken(String accessToken) {
        final byte[] accessTokenKey = serializeKey(RedisTokenConstant.ACCESS_TOKEN, accessToken);
        final byte[] accessAuthKey = serializeKey(RedisTokenConstant.ACCESS_TO_AUTH, accessToken);
        final byte[] accessTimoutKey = serializeKey(RedisTokenConstant.ACCESS_TOKEN_TIMEOUT, "");
        final List<Object> list = redisTemplate.executePipelined(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.get(accessTokenKey);
                connection.get(accessAuthKey);
                connection.get(accessTimoutKey);
                return null;
            }

        });
        if (null == list || null == list.get(0)) {
            return null;
        }
        final Authentication auth = toAuthentication(list.get(1));
        final SecurityUser user = (SecurityUser) auth.getPrincipal();
        final Object tenantId = user.getTenantId();
        final String username = user.getUsername();
        final Object timeoutValue = list.get(2);
        final int accessTokenTimeout = null == timeoutValue ? defaultAccessTokenTimeout : (int) timeoutValue;
        final byte[] unameTokenKey = serializeKey(RedisTokenConstant.UNAME_TO_ACCESS,
                String.format("%s:%s", String.valueOf(tenantId), username));
        redisTemplate.executePipelined(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.expire(accessTokenKey, accessTokenTimeout);
                connection.expire(accessAuthKey, accessTokenTimeout);
                connection.expire(unameTokenKey, accessTokenTimeout);
                return null;
            }

        });

        return auth;
    }

    /**
     *
     * @see com.jarvis.framework.oauth2.server.token.support.AbstractRedisOauth2TokenService#getRedisTemplate()
     */
    @Override
    protected RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    /**
     *
     * @see com.jarvis.framework.oauth2.server.token.support.AbstractRedisOauth2TokenService#getAccessTokenTimeout()
     */
    @Override
    protected int getAccessTokenTimeout() {
        return defaultAccessTokenTimeout;
    }

}
