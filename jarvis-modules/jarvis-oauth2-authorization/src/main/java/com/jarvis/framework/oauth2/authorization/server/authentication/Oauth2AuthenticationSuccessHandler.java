package com.jarvis.framework.oauth2.authorization.server.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarvis.framework.oauth2.authorization.server.token.Oauth2TokenCreationService;
import com.jarvis.framework.web.rest.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月22日
 */
public class Oauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static Logger log = LoggerFactory.getLogger(Oauth2AuthenticationSuccessHandler.class);

    private final ObjectMapper objectMapper;

    private final Oauth2TokenCreationService tokenCreationService;

    public Oauth2AuthenticationSuccessHandler(ObjectMapper objectMapper,
                                              Oauth2TokenCreationService tokenCreationService) {
        this.objectMapper = objectMapper;
        this.tokenCreationService = tokenCreationService;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.web.authentication.
     * AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * org.springframework.security.core.Authentication)
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        if (log.isDebugEnabled()) {
            log.debug("登录成功");
        }

        response.setContentType("application/json;charset=UTF-8");
        final PrintWriter writer = response.getWriter();
        try {
            final OAuth2AccessTokenResponse accessTokenResponse = tokenCreationService
                    .createAccessTokenResponse(authentication);
            writer.write(objectMapper
                    .writeValueAsString(RestResponse.success(toAccessTokenResponse(accessTokenResponse))));
            writer.flush();
        } catch (final Exception e) {
            writer.write(objectMapper
                    .writeValueAsString(RestResponse.failure("创建令牌出错")));
            writer.flush();
        } finally {
            if (null != writer) {
                writer.close();
            }
        }

    }

    private Map<String, Object> toAccessTokenResponse(OAuth2AccessTokenResponse accessTokenResponse) {
        final Map<String, Object> responseMap = new HashMap<>();
        final OAuth2AccessToken accessToken = accessTokenResponse.getAccessToken();
        responseMap.put("accessToken", accessToken.getTokenValue());
        responseMap.put("tokenType", accessToken.getTokenType().getValue());
        responseMap.putAll(accessTokenResponse.getAdditionalParameters());

        return responseMap;
    }

}
