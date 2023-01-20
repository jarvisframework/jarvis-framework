package com.jarvis.framework.oauth2.authorization.server.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse.Builder;
import org.springframework.util.CollectionUtils;

public abstract class AccessTokenUtil {

    public static OAuth2AccessTokenResponse createAccessTokenResponse(String accessTokenValue, String refreshTokenValue, int accessTokenExp, Set<String> scopes, Map<String, Object> additionalParameters) {
        OAuth2AccessToken accessToken = createOAuth2AccessToken(accessTokenValue, accessTokenExp, scopes);
        OAuth2RefreshToken refreshToken = createOAuth2RefreshToken(refreshTokenValue);
        Builder builder = OAuth2AccessTokenResponse.withToken(accessToken.getTokenValue()).tokenType(accessToken.getTokenType()).scopes(accessToken.getScopes());
        if (accessToken.getIssuedAt() != null && accessToken.getExpiresAt() != null) {
            builder.expiresIn(ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt()));
        }

        if (refreshToken != null) {
            builder.refreshToken(refreshToken.getTokenValue());
        }

        if (!CollectionUtils.isEmpty(additionalParameters)) {
            builder.additionalParameters(additionalParameters);
        }

        return builder.build();
    }

    public static OAuth2AccessTokenResponse createAccessTokenResponse(String accessTokenValue, String refreshTokenValue, int accessTokenExp, Map<String, Object> additionalParameters) {
        return createAccessTokenResponse(accessTokenValue, refreshTokenValue, accessTokenExp, Collections.singleton("server"), additionalParameters);
    }

    public static OAuth2AccessTokenResponse createAccessTokenResponse(int accessTokenExp, Map<String, Object> additionalParameters) {
        return createAccessTokenResponse(uuid(), uuid(), accessTokenExp, Collections.singleton("server"), additionalParameters);
    }

    private static OAuth2AccessToken createOAuth2AccessToken(String accessTokenValue, int timeout, Set<String> scopes) {
        Instant expiresIn = Instant.ofEpochMilli(System.currentTimeMillis() + (long)timeout);
        return new OAuth2AccessToken(TokenType.BEARER, accessTokenValue, Instant.now(), expiresIn, scopes);
    }

    private static OAuth2RefreshToken createOAuth2RefreshToken(String refreshTokenValue) {
        OAuth2RefreshToken oAuth2RefreshToken = new OAuth2RefreshToken(refreshTokenValue, Instant.now());
        return oAuth2RefreshToken;
    }

    private static String uuid() {
        return UUID.randomUUID().toString();
    }
}
