package com.jarvis.framework.oauth2.authorization.server.token;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;

public interface Oauth2TokenStoreService {
    
    void storeAccessToken(OAuth2AccessTokenResponse var1, Authentication var2);

    Authentication getByRefreshToken(String var1);

    OAuth2AccessTokenResponse getByAuthentication(Authentication var1);

    Authentication removeAccessToken(String var1);

    void removeRefreshToken(String var1);

    void removeByAuthentication(Authentication var1);
}
