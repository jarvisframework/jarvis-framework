package com.jarvis.framework.security.authentication.sso;

public class SsoTokenModel {
    private String username;
    private String password;
    private String type;
    private long expiredAt;

    public SsoTokenModel(String username, String password, String type, long expiredAt) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.expiredAt = expiredAt;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getExpiredAt() {
        return this.expiredAt;
    }

    public void setExpiredAt(long expiredAt) {
        this.expiredAt = expiredAt;
    }
}
