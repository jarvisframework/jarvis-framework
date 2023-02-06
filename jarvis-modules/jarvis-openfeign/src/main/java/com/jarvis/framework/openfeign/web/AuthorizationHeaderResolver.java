package com.jarvis.framework.openfeign.web;

import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年6月4日
 */
public abstract class AuthorizationHeaderResolver {

    public static String resolve(HttpServletRequest request) {
        final String authorizationHeaderToken = resolveFromAuthorizationHeader(request);
        if (authorizationHeaderToken != null) {
            return authorizationHeaderToken;
        }
        final String parameterToken = resolveFromRequestParameters(request);
        if (parameterToken != null) {
            return parameterToken;
        }
        return null;
    }

    private static String resolveFromAuthorizationHeader(HttpServletRequest request) {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.startsWithIgnoreCase(authorization, "Bearer")) {
            return null;
        }
        return authorization;
    }

    private static String resolveFromRequestParameters(HttpServletRequest request) {
        final String[] values = request.getParameterValues("access_token");
        if (values == null || values.length == 0) {
            return null;
        }
        return String.format("Bearer %s", values[0]);
    }

}
