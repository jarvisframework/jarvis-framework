package com.jarvis.framework.oauth2.authorization.server.web;

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
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月20日
 */
public class Oauth2TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(
            "/oauth2/token", "POST");

    private boolean postOnly = true;

    public Oauth2TokenAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    public Oauth2TokenAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        final long start = System.currentTimeMillis();
        String username = obtainUsername(request);
        username = (username != null) ? username : "";
        username = username.trim();
        String password = obtainPassword(request);
        password = (password != null) ? password : "";
        final AuthorizationGrantType grantType = obtainGrantType(request);
        if (AuthorizationGrantType.REFRESH_TOKEN.equals(grantType)) {
            throw new AuthenticationServiceException("暂不支持RefreshToken功能");
        }
        final UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,
                password);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        final Authentication authenticate = this.getAuthenticationManager().authenticate(authRequest);
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("登录[attemptAuthentication]处理共耗时：%s ms", (System.currentTimeMillis() - start)));
        }
        return authenticate;
    }

    /**
     * Enables subclasses to override the composition of the password, such as by
     * including additional values and a separator.
     * <p>
     * This might be used for example if a postcode/zipcode was required in addition to
     * the password. A delimiter such as a pipe (|) should be used to separate the
     * password and extended value(s). The <code>AuthenticationDao</code> will need to
     * generate the expected password in a corresponding manner.
     * </p>
     *
     * @param request so that request attributes can be retrieved
     * @return the password that will be presented in the <code>Authentication</code>
     *         request token to the <code>AuthenticationManager</code>
     */
    @Nullable
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(OAuth2ParameterNames.PASSWORD);
    }

    /**
     * Enables subclasses to override the composition of the username, such as by
     * including additional values and a separator.
     *
     * @param request so that request attributes can be retrieved
     * @return the username that will be presented in the <code>Authentication</code>
     *         request token to the <code>AuthenticationManager</code>
     */
    @Nullable
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(OAuth2ParameterNames.USERNAME);
    }

    @NonNull
    protected AuthorizationGrantType obtainGrantType(HttpServletRequest request) {
        final String[] grantTypes = request.getParameterValues(OAuth2ParameterNames.GRANT_TYPE);
        if (grantTypes == null || grantTypes.length != 1) {
            throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.GRANT_TYPE);
        }
        final String grantType = grantTypes[0].toLowerCase();
        if (!supportGrantType(grantType)) {
            throwError(OAuth2ErrorCodes.UNSUPPORTED_GRANT_TYPE, OAuth2ParameterNames.GRANT_TYPE);
        }
        return new AuthorizationGrantType(grantType);
    }

    protected boolean supportGrantType(String grantType) {
        return (AuthorizationGrantType.PASSWORD.getValue().equals(grantType))
                || (AuthorizationGrantType.REFRESH_TOKEN.getValue().equals(grantType));
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication
     * request's details property.
     *
     * @param request that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details
     *            set
     */
    protected void setDetails(HttpServletRequest request, AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    /**
     * Defines whether only HTTP POST requests will be allowed by this filter. If set to
     * true, and an authentication request is received which is not a POST request, an
     * exception will be raised immediately and authentication will not be attempted. The
     * <tt>unsuccessfulAuthentication()</tt> method will be called as if handling a failed
     * authentication.
     * <p>
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    private static void throwError(String errorCode, String parameterName) {
        final OAuth2Error error = new OAuth2Error(errorCode, "OAuth 2.0 Parameter: " + parameterName,
                "https://tools.ietf.org/html/rfc6749#section-5.2");
        throw new OAuth2AuthenticationException(error);
    }

}
