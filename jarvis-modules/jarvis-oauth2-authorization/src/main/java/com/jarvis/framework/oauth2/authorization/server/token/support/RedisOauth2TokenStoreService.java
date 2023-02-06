package com.jarvis.framework.oauth2.authorization.server.token.support;

import com.jarvis.framework.oauth2.authorization.server.config.Oauth2ServerProperties;
import com.jarvis.framework.oauth2.authorization.server.token.Oauth2TokenStoreService;
import com.jarvis.framework.oauth2.authorization.server.util.AccessTokenUtil;
import com.jarvis.framework.oauth2.server.token.constant.RedisTokenConstant;
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

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月21日
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RedisOauth2TokenStoreService extends AbstractRedisOauth2TokenService implements Oauth2TokenStoreService {

    private static final String ACCESS_TOKEN = RedisTokenConstant.ACCESS_TOKEN;

    //private static final String REFRESH_TOKEN = RedisTokenConstant.REFRESH_TOKEN;

    //private static final String ACCESS_TO_REFRESH = RedisTokenKeyPrefix.ACCESS_TO_REFRESH;

    //private static final String REFRESH_TO_ACCESS = RedisTokenConstant.REFRESH_TO_ACCESS;

    private static final String UNAME_TO_ACCESS = RedisTokenConstant.UNAME_TO_ACCESS;

    private static final String ACCESS_TO_AUTH = RedisTokenConstant.ACCESS_TO_AUTH;

    private final RedisTemplate<String, Object> redisTemplate;

    private final Oauth2ServerProperties properties;

    /**
     * @param redisTemplate
     * @param accessTokenTimeout
     * @param refreshTokenTimeout
     */
    public RedisOauth2TokenStoreService(RedisConnectionFactory redisConnectionFactory,
                                        Oauth2ServerProperties properties) {
        super();
        this.redisTemplate = initRedisTemplate(redisConnectionFactory);
        this.properties = properties;
        initAccessTokenTimeout();
    }

    private void initAccessTokenTimeout() {
        redisTemplate.opsForValue().set(RedisTokenConstant.ACCESS_TOKEN_TIMEOUT, getAccessTokenTimeout());
    }

    /*private RedisTemplate<String, Object> initRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }*/

    /**
     * @see com.jarvis.framework.oauth2.authorization.server.token.Oauth2TokenStoreService#storeAccessToken(
     *      org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse,
     *      org.springframework.security.core.Authentication)
     */
    @Override
    public void storeAccessToken(OAuth2AccessTokenResponse token, Authentication auth) {
        final OAuth2AccessToken accessToken = token.getAccessToken();
        final OAuth2RefreshToken refreshToken = token.getRefreshToken();
        final String accessTokenValue = accessToken.getTokenValue();
        final String refreshTokenValue = refreshToken.getTokenValue();
        final SecurityUser user = (SecurityUser) auth.getPrincipal();
        final byte[] accessTokenKey = serializeKey(ACCESS_TOKEN, accessTokenValue);
        final byte[] accessToAuthKey = serializeKey(ACCESS_TO_AUTH, accessTokenValue);
        final Object tenantId = user.getTenantId();
        final String username = user.getUsername();
        final byte[] unameTokenKey = serializeKey(UNAME_TO_ACCESS,
                String.format("%s:%s", String.valueOf(tenantId), username));
        //final byte[] refreshAccessKey = serializeKey(REFRESH_TO_ACCESS, refreshTokenValue);
        final String unameToken = String.format("%s:%s", accessTokenValue, refreshTokenValue);

        final RedisSerializer serializer = redisTemplate.getValueSerializer();
        final int accessTokenTimeout = getAccessTokenTimeout();
        final List<Object> list = redisTemplate.executePipelined(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.get(unameTokenKey);
                connection.setEx(accessTokenKey, accessTokenTimeout, serializer.serialize(refreshTokenValue));
                connection.setEx(accessToAuthKey, accessTokenTimeout, serializeValue(auth));
                connection.setEx(unameTokenKey, accessTokenTimeout, serializer.serialize(unameToken));
                //connection.setEx(refreshAccessKey, refreshTokenTimeout, serializer.serialize(accessTokenValue));
                return null;
            }

        });

        if (!properties.isConcurrentAccess() && null != list && null != list.get(0)) {
            final String str = (String) list.get(0);
            final String[] tokens = str.split(":");
            removeAccessToken(tokens[0]);
        }

    }

    /**
     *
     * @see com.jarvis.framework.oauth2.authorization.server.token.Oauth2TokenStoreService#getRefreshToken(java.lang.String)
     */
    @Override
    public Authentication getByRefreshToken(String refreshToken) {
        /*final byte[] refreshTokenKey = serializeKey(REFRESH_TOKEN, refreshToken);
        final String accessToken = (String) redisTemplate.opsForValue().get(refreshTokenKey);
        if (null == accessToken) {
            return null;
        }*/
        return null;
    }

    /**
     *
     * @see com.jarvis.framework.oauth2.authorization.server.token.Oauth2TokenStoreService#getByAuthentication(org.springframework.security.core.Authentication)
     */
    @Override
    public OAuth2AccessTokenResponse getByAuthentication(Authentication auth) {
        final SecurityUser user = (SecurityUser) auth.getPrincipal();
        final Object tenantId = user.getTenantId();
        final String username = user.getUsername();
        final byte[] unameTokenKey = serializeKey(UNAME_TO_ACCESS,
                String.format("%s:%s", String.valueOf(tenantId), username));
        final List<Object> accessTokens = redisTemplate.executePipelined(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.get(unameTokenKey);
                return null;
            }

        });
        if (null == accessTokens || accessTokens.isEmpty() || null == accessTokens.get(0)) {
            return null;
        }
        final String unameToken = (String) accessTokens.get(0);
        final String[] tokens = unameToken.split(":");
        final String accessToken = tokens[0];
        final String refreshToken = tokens[1];

        final byte[] accessTokenKey = serializeKey(ACCESS_TOKEN, accessToken);
        final byte[] accessToAuthKey = serializeKey(ACCESS_TO_AUTH, accessToken);
        //final byte[] accessToRefreshKey = serializeKey(ACCESS_TO_REFRESH, accessToken);
        //final RedisSerializer serializer = redisTemplate.getValueSerializer();
        final int accessTokenTimeout = getAccessTokenTimeout();
        redisTemplate.executePipelined(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.expire(accessTokenKey, accessTokenTimeout);
                connection.setEx(accessToAuthKey, accessTokenTimeout, serializeValue(auth));
                //connection.expire(accessToRefreshKey, accessTokenExp);
                connection.expire(unameTokenKey, accessTokenTimeout);
                return null;
            }

        });

        final Map<String, Object> additionalParameters = new HashMap<>();
        additionalParameters.put("principal", auth.getPrincipal());
        return AccessTokenUtil.createAccessTokenResponse(accessToken, refreshToken, accessTokenTimeout,
                additionalParameters);
    }

    /**
     *
     * @see com.jarvis.framework.oauth2.authorization.server.token.Oauth2TokenStoreService#removeAccessToken(java.lang.String)
     */
    @Override
    public Authentication removeAccessToken(String accessTokenValue) {
        final byte[] accessTokenKey = serializeKey(ACCESS_TOKEN, accessTokenValue);
        final byte[] accessToAuthKey = serializeKey(ACCESS_TO_AUTH, accessTokenValue);
        final List<Object> accessTokens = redisTemplate.executePipelined(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.get(accessToAuthKey);
                connection.get(accessTokenKey);
                return null;
            }

        });
        if (null == accessTokens || accessTokens.isEmpty() || null == accessTokens.get(0)) {
            return null;
        }
        //String refreshTokenValue = null;
        Authentication auth = null;
        // auth = (Authentication) accessTokens.get(0);
        auth = toAuthentication(accessTokens.get(0));
        //refreshTokenValue = (String) accessTokens.get(1);
        final SecurityUser user = (SecurityUser) auth.getPrincipal();
        final Object tenantId = user.getTenantId();
        final String username = user.getUsername();
        final byte[] unameTokenKey = serializeKey(UNAME_TO_ACCESS,
                String.format("%s:%s", String.valueOf(tenantId), username));
        //final byte[] refreshAccessKey = serializeKey(REFRESH_TO_ACCESS, refreshTokenValue);
        redisTemplate.executePipelined(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.del(unameTokenKey, accessToAuthKey, accessTokenKey/*, refreshAccessKey*/);
                return null;
            }

        });
        return auth;
    }

    /**
     *
     * @see com.jarvis.framework.oauth2.authorization.server.token.Oauth2TokenStoreService#removeRefreshToken(java.lang.String)
     */
    @Override
    public void removeRefreshToken(String refreshToken) {
        // TODO Auto-generated method stub

    }

    /**
     *
     * @see com.jarvis.framework.oauth2.authorization.server.token.Oauth2TokenStoreService#removeByAuthentication(org.springframework.security.core.Authentication)
     */
    @Override
    public void removeByAuthentication(Authentication auth) {
        final SecurityUser user = (SecurityUser) auth.getPrincipal();
        final Object tenantId = user.getTenantId();
        final String username = user.getUsername();
        final byte[] unameTokenKey = serializeKey(UNAME_TO_ACCESS,
                String.format("%s:%s", String.valueOf(tenantId), username));
        final List<Object> accessTokens = redisTemplate.executePipelined(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.get(unameTokenKey);
                return null;
            }

        });
        if (null == accessTokens || accessTokens.isEmpty() || null == accessTokens.get(0)) {
            return;
        }
        final String unameToken = (String) accessTokens.get(0);
        final String[] tokens = unameToken.split(":");
        final String accessToken = tokens[0];
        //final String refreshToken = tokens[1];

        final byte[] accessTokenKey = serializeKey(ACCESS_TOKEN, accessToken);
        final byte[] accessToAuthKey = serializeKey(ACCESS_TO_AUTH, accessToken);
        //final byte[] refreshAccessKey = serializeKey(REFRESH_TO_ACCESS, refreshToken);

        redisTemplate.executePipelined(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.del(unameTokenKey, accessToAuthKey, accessTokenKey/*, refreshAccessKey*/);
                return null;
            }

        });
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
        return properties.getAccessTokenTimeout() * 60;
    }

}
