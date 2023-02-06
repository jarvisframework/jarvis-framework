package com.jarvis.framework.oauth2.authorization.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月23日
 */
@ConfigurationProperties(prefix = "spring.security.oauth2.server")
public class Oauth2ServerProperties {

    /**
     * AccessToken过期时间单位为分钟，默认为6个小时
     */
    private int accessTokenTimeout = 6 * 60;

    /**
     * RefreshToken过期时间单位为分钟，默认为24个小时
     */
    //private int refreshTokenTimeout = 24 * 60 * 60;

    /**
     * 是否允许并发登录
     */
    private boolean concurrentAccess = true;

    /**
     * 并发登录共用同一个token
     */
    private boolean concurrentShared = true;

    /**
     * @return the accessTokenTimeout
     */
    public int getAccessTokenTimeout() {
        return accessTokenTimeout;
    }

    /**
     * @param accessTokenTimeout the accessTokenTimeout to set
     */
    public void setAccessTokenTimeout(int accessTokenTimeout) {
        this.accessTokenTimeout = accessTokenTimeout;
    }

    /**
     * @return the refreshTokenTimeout
     */
    /*public int getRefreshTokenTimeout() {
        return refreshTokenTimeout;
    }*/

    /**
     * @param refreshTokenTimeout the refreshTokenTimeout to set
     */
    /*public void setRefreshTokenTimeout(int refreshTokenTimeout) {
        this.refreshTokenTimeout = refreshTokenTimeout;
    }*/

    /**
     * @return the concurrentAccess
     */
    public boolean isConcurrentAccess() {
        return concurrentAccess;
    }

    /**
     * @param concurrentAccess the concurrentAccess to set
     */
    public void setConcurrentAccess(boolean concurrentAccess) {
        this.concurrentAccess = concurrentAccess;
    }

    /**
     * @return the concurrentShared
     */
    public boolean isConcurrentShared() {
        return concurrentShared;
    }

    /**
     * @param concurrentShared the concurrentShared to set
     */
    public void setConcurrentShared(boolean concurrentShared) {
        this.concurrentShared = concurrentShared;
    }

}
