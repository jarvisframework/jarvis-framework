package com.jarvis.framework.oauth2.authorization.server.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class Oauth2TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/oauth2/token", "POST");
    private boolean postOnly = true;

    public Oauth2TokenAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    public Oauth2TokenAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            long start = System.currentTimeMillis();
            String username = this.obtainUsername(request);
            username = username != null ? username : "";
            username = username.trim();
            String password = this.obtainPassword(request);
            password = password != null ? password : "";
            AuthorizationGrantType grantType = this.obtainGrantType(request);
            if (AuthorizationGrantType.REFRESH_TOKEN.equals(grantType)) {
                throw new AuthenticationServiceException("暂不支持RefreshToken功能");
            } else {
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
                this.setDetails(request, authRequest);
                Authentication authenticate = this.getAuthenticationManager().authenticate(authRequest);
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug(String.format("登录[attemptAuthentication]处理共耗时：%s ms", System.currentTimeMillis() - start));
                }

                return authenticate;
            }
        }
    }

    @Nullable
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter("password");
    }

    @Nullable
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter("username");
    }

    @NonNull
    protected AuthorizationGrantType obtainGrantType(HttpServletRequest request) {
        String[] grantTypes = request.getParameterValues("grant_type");
        if (grantTypes == null || grantTypes.length != 1) {
            throwError("invalid_request", "grant_type");
        }

        String grantType = grantTypes[0].toLowerCase();
        if (!this.supportGrantType(grantType)) {
            throwError("unsupported_grant_type", "grant_type");
        }

        return new AuthorizationGrantType(grantType);
    }

    protected boolean supportGrantType(String grantType) {
        return AuthorizationGrantType.PASSWORD.getValue().equals(grantType) || AuthorizationGrantType.REFRESH_TOKEN.getValue().equals(grantType);
    }

    protected void setDetails(HttpServletRequest request, AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    private static void throwError(String errorCode, String parameterName) {
        OAuth2Error error = new OAuth2Error(errorCode, "OAuth 2.0 Parameter: " + parameterName, "https://tools.ietf.org/html/rfc6749#section-5.2");
        throw new OAuth2AuthenticationException(error);
    }
}
