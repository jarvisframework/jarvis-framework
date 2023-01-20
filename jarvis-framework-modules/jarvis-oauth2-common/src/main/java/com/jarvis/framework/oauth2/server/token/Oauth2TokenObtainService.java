package com.jarvis.framework.oauth2.server.token;

import org.springframework.security.core.Authentication;

public interface Oauth2TokenObtainService {

    Authentication getByAccessToken(String var1);

}
