package com.jarvis.framework.oauth2.authorization.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
    prefix = "spring.security.oauth2.server"
)
public class Oauth2ServerProperties {
    private int accessTokenTimeout = 360;
    private boolean concurrentAccess = true;
    private boolean concurrentShared = true;

    public int getAccessTokenTimeout() {
        return this.accessTokenTimeout;
    }

    public void setAccessTokenTimeout(int accessTokenTimeout) {
        this.accessTokenTimeout = accessTokenTimeout;
    }

    public boolean isConcurrentAccess() {
        return this.concurrentAccess;
    }

    public void setConcurrentAccess(boolean concurrentAccess) {
        this.concurrentAccess = concurrentAccess;
    }

    public boolean isConcurrentShared() {
        return this.concurrentShared;
    }

    public void setConcurrentShared(boolean concurrentShared) {
        this.concurrentShared = concurrentShared;
    }
}
