package com.jarvis.framework.oauth2.authorization.server.authentication;

import com.jarvis.framework.oauth2.authorization.server.token.Oauth2TokenStoreService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月30日
 */
public class Oauth2LogoutHandler implements LogoutHandler, ApplicationEventPublisherAware {

    private ApplicationEventPublisher eventPublisher;

    private final Oauth2TokenStoreService oauth2TokenStoreService;

    private final BearerTokenResolver bearerTokenResolver;

    /**
     * @param objectMapper
     * @param responseType
     */
    public Oauth2LogoutHandler(Oauth2TokenStoreService oauth2TokenStoreService,
                               BearerTokenResolver bearerTokenResolver) {
        super();
        this.oauth2TokenStoreService = oauth2TokenStoreService;
        this.bearerTokenResolver = bearerTokenResolver;
    }

    /**
     *
     * @see org.springframework.security.web.authentication.logout.LogoutHandler#logout(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String token = bearerTokenResolver.resolve(request);
        if (null == token) {
            final OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST, "请携带token进行退出", null);
            throw new OAuth2AuthenticationException(error);
        }
        final Authentication tokenAuthentication = oauth2TokenStoreService.removeAccessToken(token);
        if (null == authentication && null != tokenAuthentication) {
            this.eventPublisher.publishEvent(new LogoutSuccessEvent(tokenAuthentication));
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

}
