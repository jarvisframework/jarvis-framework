package com.jarvis.framework.oauth2.authorization.server.token;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;

public interface Oauth2TokenCreationService {
    OAuth2AccessTokenResponse createAccessTokenResponse(Authentication var1);
}
