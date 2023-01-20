package com.jarvis.framework.oauth2.authorization.server.token.support;

import com.jarvis.framework.oauth2.authorization.server.config.Oauth2ServerProperties;
import com.jarvis.framework.oauth2.authorization.server.token.Oauth2TokenStoreService;
import com.jarvis.framework.oauth2.authorization.server.util.AccessTokenUtil;
import com.jarvis.framework.oauth2.server.token.support.AbstractRedisOauth2TokenService;
import com.jarvis.framework.security.model.SecurityUser;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedisOauth2TokenStoreService extends AbstractRedisOauth2TokenService implements Oauth2TokenStoreService {
    private static final String ACCESS_TOKEN = "access_token:";
    private static final String UNAME_TO_ACCESS = "uname_to_access:";
    private static final String ACCESS_TO_AUTH = "access_to_auth:";
    private final RedisTemplate<String, Object> redisTemplate;
    private final Oauth2ServerProperties properties;

    public RedisOauth2TokenStoreService(RedisConnectionFactory redisConnectionFactory, Oauth2ServerProperties properties) {
        this.redisTemplate = this.initRedisTemplate(redisConnectionFactory);
        this.properties = properties;
        this.initAccessTokenTimeout();
    }

    private void initAccessTokenTimeout() {
        this.redisTemplate.opsForValue().set("access_token:timeout", this.getAccessTokenTimeout());
    }

    public void storeAccessToken(OAuth2AccessTokenResponse token, final Authentication auth) {
        OAuth2AccessToken accessToken = token.getAccessToken();
        OAuth2RefreshToken refreshToken = token.getRefreshToken();
        String accessTokenValue = accessToken.getTokenValue();
        final String refreshTokenValue = refreshToken.getTokenValue();
        SecurityUser user = (SecurityUser)auth.getPrincipal();
        final byte[] accessTokenKey = this.serializeKey("access_token:", accessTokenValue);
        final byte[] accessToAuthKey = this.serializeKey("access_to_auth:", accessTokenValue);
        Object tenantId = user.getTenantId();
        String username = user.getUsername();
        final byte[] unameTokenKey = this.serializeKey("uname_to_access:", String.format("%s:%s", String.valueOf(tenantId), username));
        final String unameToken = String.format("%s:%s", accessTokenValue, refreshTokenValue);
        final RedisSerializer serializer = this.redisTemplate.getValueSerializer();
        final int accessTokenTimeout = this.getAccessTokenTimeout();
        List<Object> list = this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.get(unameTokenKey);
                connection.setEx(accessTokenKey, (long)accessTokenTimeout, serializer.serialize(refreshTokenValue));
                connection.setEx(accessToAuthKey, (long)accessTokenTimeout, RedisOauth2TokenStoreService.this.serializeValue(auth));
                connection.setEx(unameTokenKey, (long)accessTokenTimeout, serializer.serialize(unameToken));
                return null;
            }
        });
        if (!this.properties.isConcurrentAccess() && null != list && null != list.get(0)) {
            String str = (String)list.get(0);
            String[] tokens = str.split(":");
            this.removeAccessToken(tokens[0]);
        }

    }

    public Authentication getByRefreshToken(String refreshToken) {
        return null;
    }

    public OAuth2AccessTokenResponse getByAuthentication(final Authentication auth) {
        SecurityUser user = (SecurityUser)auth.getPrincipal();
        Object tenantId = user.getTenantId();
        String username = user.getUsername();
        final byte[] unameTokenKey = this.serializeKey("uname_to_access:", String.format("%s:%s", String.valueOf(tenantId), username));
        List<Object> accessTokens = this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.get(unameTokenKey);
                return null;
            }
        });
        if (null != accessTokens && !accessTokens.isEmpty() && null != accessTokens.get(0)) {
            String unameToken = (String)accessTokens.get(0);
            String[] tokens = unameToken.split(":");
            String accessToken = tokens[0];
            String refreshToken = tokens[1];
            final byte[] accessTokenKey = this.serializeKey("access_token:", accessToken);
            final byte[] accessToAuthKey = this.serializeKey("access_to_auth:", accessToken);
            final int accessTokenTimeout = this.getAccessTokenTimeout();
            this.redisTemplate.executePipelined(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.expire(accessTokenKey, (long)accessTokenTimeout);
                    connection.setEx(accessToAuthKey, (long)accessTokenTimeout, RedisOauth2TokenStoreService.this.serializeValue(auth));
                    connection.expire(unameTokenKey, (long)accessTokenTimeout);
                    return null;
                }
            });
            Map<String, Object> additionalParameters = new HashMap();
            additionalParameters.put("principal", auth.getPrincipal());
            return AccessTokenUtil.createAccessTokenResponse(accessToken, refreshToken, accessTokenTimeout, additionalParameters);
        } else {
            return null;
        }
    }

    public Authentication removeAccessToken(String accessTokenValue) {
        final byte[] accessTokenKey = this.serializeKey("access_token:", accessTokenValue);
        final byte[] accessToAuthKey = this.serializeKey("access_to_auth:", accessTokenValue);
        List<Object> accessTokens = this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.get(accessToAuthKey);
                connection.get(accessTokenKey);
                return null;
            }
        });
        if (null != accessTokens && !accessTokens.isEmpty() && null != accessTokens.get(0)) {
            Authentication auth = null;
            auth = this.toAuthentication(accessTokens.get(0));
            SecurityUser user = (SecurityUser)auth.getPrincipal();
            Object tenantId = user.getTenantId();
            String username = user.getUsername();
            final byte[] unameTokenKey = this.serializeKey("uname_to_access:", String.format("%s:%s", String.valueOf(tenantId), username));
            this.redisTemplate.executePipelined(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.del(new byte[][]{unameTokenKey, accessToAuthKey, accessTokenKey});
                    return null;
                }
            });
            return auth;
        } else {
            return null;
        }
    }

    public void removeRefreshToken(String refreshToken) {
    }

    public void removeByAuthentication(Authentication auth) {
        SecurityUser user = (SecurityUser)auth.getPrincipal();
        Object tenantId = user.getTenantId();
        String username = user.getUsername();
        final byte[] unameTokenKey = this.serializeKey("uname_to_access:", String.format("%s:%s", String.valueOf(tenantId), username));
        List<Object> accessTokens = this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.get(unameTokenKey);
                return null;
            }
        });
        if (null != accessTokens && !accessTokens.isEmpty() && null != accessTokens.get(0)) {
            String unameToken = (String)accessTokens.get(0);
            String[] tokens = unameToken.split(":");
            String accessToken = tokens[0];
            final byte[] accessTokenKey = this.serializeKey("access_token:", accessToken);
            final byte[] accessToAuthKey = this.serializeKey("access_to_auth:", accessToken);
            this.redisTemplate.executePipelined(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.del(new byte[][]{unameTokenKey, accessToAuthKey, accessTokenKey});
                    return null;
                }
            });
        }
    }

    protected RedisTemplate<String, Object> getRedisTemplate() {
        return this.redisTemplate;
    }

    protected int getAccessTokenTimeout() {
        return this.properties.getAccessTokenTimeout() * 60;
    }
}
