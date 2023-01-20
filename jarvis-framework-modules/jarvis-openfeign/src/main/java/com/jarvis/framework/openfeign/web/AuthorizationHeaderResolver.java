package com.jarvis.framework.openfeign.web;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public abstract class AuthorizationHeaderResolver {
    public AuthorizationHeaderResolver() {
    }

    public static String resolve(HttpServletRequest request) {
        String authorizationHeaderToken = resolveFromAuthorizationHeader(request);
        if (authorizationHeaderToken != null) {
            return authorizationHeaderToken;
        } else {
            String parameterToken = resolveFromRequestParameters(request);
            return parameterToken != null ? parameterToken : null;
        }
    }

    private static String resolveFromAuthorizationHeader(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        return !StringUtils.startsWithIgnoreCase(authorization, "Bearer") ? null : authorization;
    }

    private static String resolveFromRequestParameters(HttpServletRequest request) {
        String[] values = request.getParameterValues("access_token");
        return values != null && values.length != 0 ? String.format("Bearer %s", values[0]) : null;
    }
}
