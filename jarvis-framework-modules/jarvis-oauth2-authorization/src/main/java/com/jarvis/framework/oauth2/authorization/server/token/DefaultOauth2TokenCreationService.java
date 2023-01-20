package com.jarvis.framework.oauth2.authorization.server.token;

public class DefaultOauth2TokenCreationService implements Oauth2TokenCreationService {
    private final Oauth2TokenStoreService tokenStoreService;
    private final Oauth2ServerProperties properties;

    public DefaultOauth2TokenCreationService(Oauth2TokenStoreService tokenStoreService, Oauth2ServerProperties properties) {
        this.tokenStoreService = tokenStoreService;
        this.properties = properties;
    }

    public OAuth2AccessTokenResponse createAccessTokenResponse(Authentication auth) {
        OAuth2AccessTokenResponse accessTokenResponse;
        if (this.properties.isConcurrentAccess()) {
            if (this.properties.isConcurrentShared()) {
                accessTokenResponse = this.tokenStoreService.getByAuthentication(auth);
                if (null != accessTokenResponse) {
                    return accessTokenResponse;
                }
            }
        } else {
            this.tokenStoreService.removeByAuthentication(auth);
        }

        Map<String, Object> additionalParameters = new HashMap();
        additionalParameters.put("principal", auth.getPrincipal());
        accessTokenResponse = AccessTokenUtil.createAccessTokenResponse(this.properties.getAccessTokenTimeout(), additionalParameters);
        this.tokenStoreService.storeAccessToken(accessTokenResponse, auth);
        return accessTokenResponse;
    }
}
