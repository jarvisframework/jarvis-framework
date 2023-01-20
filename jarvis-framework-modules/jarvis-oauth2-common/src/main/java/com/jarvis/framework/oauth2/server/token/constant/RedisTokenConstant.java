package com.jarvis.framework.oauth2.server.token.constant;

public abstract class RedisTokenConstant {
    public static final String ACCESS_TOKEN = "access_token:";
    public static final String REFRESH_TOKEN = "refresh_token:";
    public static final String ACCESS_TO_REFRESH = "access_to_refresh:";
    public static final String REFRESH_TO_ACCESS = "refresh_to_access:";
    public static final String UNAME_TO_ACCESS = "uname_to_access:";
    public static final String ACCESS_TO_AUTH = "access_to_auth:";
    public static final String ACCESS_TOKEN_TIMEOUT = "access_token:timeout";
}
