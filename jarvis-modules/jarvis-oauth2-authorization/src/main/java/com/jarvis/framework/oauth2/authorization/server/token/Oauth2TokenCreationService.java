package com.jarvis.framework.oauth2.authorization.server.token;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月22日
 */
public interface Oauth2TokenCreationService {

    OAuth2AccessTokenResponse createAccessTokenResponse(Authentication auth);

}
