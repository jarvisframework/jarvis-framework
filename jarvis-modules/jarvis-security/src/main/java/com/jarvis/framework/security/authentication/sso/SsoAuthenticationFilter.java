package com.jarvis.framework.security.authentication.sso;

import com.jarvis.framework.security.authentication.sso.converter.SsoAuthenticationConverter;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年3月22日
 */
public class SsoAuthenticationFilter extends OncePerRequestFilter {

    private final SsoAuthenticationConverter authenticationConverter = new SsoAuthenticationConverter();

    private final AuthenticationDetailsSource<HttpServletRequest,
            ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    private static final RequestMatcher REQUEST_MATCHER = new RequestHeaderRequestMatcher(
            SsoAuthenticationConverter.AUTHENTICATION_HEADER_TOKEN);

    private final AuthenticationManager authenticationManager;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    public SsoAuthenticationFilter(AuthenticationManager authenticationManager,
                                   AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationManager = authenticationManager;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!REQUEST_MATCHER.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            final Authentication authenticationResult = attemptAuthentication(request, response);
            final SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authenticationResult);
            SecurityContextHolder.setContext(context);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug(LogMessage.format("Set SecurityContextHolder to %s", authenticationResult));
            }
            filterChain.doFilter(request, response);
        } catch (final AuthenticationException failed) {
            SecurityContextHolder.clearContext();
            this.logger.trace("Failed to process authentication request", failed);
            this.authenticationEntryPoint.commence(request, response, failed);
        }
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        // Allow subclasses to set the "details" property
        final SsoAuthenticationToken authRequest = authenticationConverter.convert(request);
        setDetails(request, authRequest);
        return this.authenticationManager.authenticate(authRequest);
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication
     * request's details property.
     *
     * @param request that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details
     *            set
     */
    protected void setDetails(HttpServletRequest request, SsoAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

}
