package com.jarvis.framework.oauth2.authorization.server.authentication;

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
