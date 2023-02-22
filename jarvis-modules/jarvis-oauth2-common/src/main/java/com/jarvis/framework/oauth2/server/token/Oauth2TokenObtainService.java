package com.jarvis.framework.oauth2.server.token;

import org.springframework.security.core.Authentication;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月23日
 */
public interface Oauth2TokenObtainService {

    /**
     * 获取AccessToken
     *
     * @param accessToken
     */
    Authentication getByAccessToken(String accessToken);

}
