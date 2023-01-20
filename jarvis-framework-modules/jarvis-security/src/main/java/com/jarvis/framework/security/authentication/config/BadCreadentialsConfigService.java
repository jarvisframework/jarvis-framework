package com.jarvis.framework.security.authentication.config;

public class BadCreadentialsConfigService {
    private final BadCreadentialsProperties properties;

    public BadCreadentialsConfigService(BadCreadentialsProperties properties) {
        this.properties = properties;
    }

    public void setEnabled(boolean enabled) {
        this.properties.setEnabled(enabled);
    }

    public void setCount(int count) {
        this.properties.setCount(count);
    }

    public void setTimeout(int timeout) {
        this.properties.setTimeout(timeout);
    }
}
