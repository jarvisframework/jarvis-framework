package com.jarvis.framework.oauth2.authorization.server.authentication;

import com.jarvis.framework.oauth2.authorization.server.token.Oauth2TokenStoreService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Oauth2LogoutHandler implements LogoutHandler, ApplicationEventPublisherAware {
    private ApplicationEventPublisher eventPublisher;
    private final Oauth2TokenStoreService oauth2TokenStoreService;
    private final BearerTokenResolver bearerTokenResolver;

    public Oauth2LogoutHandler(Oauth2TokenStoreService oauth2TokenStoreService, BearerTokenResolver bearerTokenResolver) {
        this.oauth2TokenStoreService = oauth2TokenStoreService;
        this.bearerTokenResolver = bearerTokenResolver;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = this.bearerTokenResolver.resolve(request);
        if (null == token) {
            OAuth2Error error = new OAuth2Error("invalid_request", "请携带token进行退出", (String)null);
            throw new OAuth2AuthenticationException(error);
        } else {
            Authentication tokenAuthentication = this.oauth2TokenStoreService.removeAccessToken(token);
            if (null == authentication && null != tokenAuthentication) {
                this.eventPublisher.publishEvent(new LogoutSuccessEvent(tokenAuthentication));
            }

        }
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }
}
