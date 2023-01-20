package com.jarvis.framework.oauth2.server.token.support;

import com.jarvis.framework.oauth2.server.token.Oauth2TokenObtainService;
import com.jarvis.framework.security.model.SecurityUser;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;

import java.util.List;

public class RedisOauth2TokenObtainService extends AbstractRedisOauth2TokenService implements Oauth2TokenObtainService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final int defaultAccessTokenTimeout = 21600;

    public RedisOauth2TokenObtainService(RedisConnectionFactory redisConnectionFactory) {
        this.redisTemplate = this.initRedisTemplate(redisConnectionFactory);
    }

    public Authentication getByAccessToken(String accessToken) {
        final byte[] accessTokenKey = this.serializeKey("access_token:", accessToken);
        final byte[] accessAuthKey = this.serializeKey("access_to_auth:", accessToken);
        final byte[] accessTimoutKey = this.serializeKey("access_token:timeout", "");
        List<Object> list = this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.get(accessTokenKey);
                connection.get(accessAuthKey);
                connection.get(accessTimoutKey);
                return null;
            }
        });
        if (null != list && null != list.get(0)) {
            Authentication auth = this.toAuthentication(list.get(1));
            SecurityUser user = (SecurityUser)auth.getPrincipal();
            Object tenantId = user.getTenantId();
            String username = user.getUsername();
            Object timeoutValue = list.get(2);
            final int accessTokenTimeout = null == timeoutValue ? 21600 : (Integer)timeoutValue;
            final byte[] unameTokenKey = this.serializeKey("uname_to_access:", String.format("%s:%s", String.valueOf(tenantId), username));
            this.redisTemplate.executePipelined(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.expire(accessTokenKey, (long)accessTokenTimeout);
                    connection.expire(accessAuthKey, (long)accessTokenTimeout);
                    connection.expire(unameTokenKey, (long)accessTokenTimeout);
                    return null;
                }
            });
            return auth;
        } else {
            return null;
        }
    }

    protected RedisTemplate<String, Object> getRedisTemplate() {
        return this.redisTemplate;
    }

    protected int getAccessTokenTimeout() {
        return 21600;
    }
}
