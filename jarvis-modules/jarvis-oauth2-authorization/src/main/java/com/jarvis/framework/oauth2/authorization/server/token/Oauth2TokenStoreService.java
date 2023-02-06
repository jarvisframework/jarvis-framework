package com.jarvis.framework.oauth2.authorization.server.token;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月21日
 */
public interface Oauth2TokenStoreService {

    /**
     * 保存AccessToken
     *
     * @param token
     * @param auth
     */
    void storeAccessToken(OAuth2AccessTokenResponse token, Authentication auth);

    /**
     * 获取RefreshToken
     *
     * @param refreshToken
     */
    Authentication getByRefreshToken(String refreshToken);

    /**
     * 根据Authentication获取
     *
     * @param auth
     */
    OAuth2AccessTokenResponse getByAuthentication(Authentication auth);

    /**
     * 移除AccessToken
     *
     * @param accessToken
     */
    Authentication removeAccessToken(String accessToken);

    /**
     * 移除RefreshToken
     *
     * @param refreshToken
     */
    void removeRefreshToken(String refreshToken);

    /**
     * 根据Authentication删除
     *
     * @param auth
     */
    void removeByAuthentication(Authentication auth);

}
