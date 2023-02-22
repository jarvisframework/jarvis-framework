package com.jarvis.framework.security.authentication.sso;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年3月18日
 */
public class SsoTokenModel {

    private String username;

    private String password;

    private String type;

    private long expiredAt;

    /**
     * @param username
     * @param password
     * @param type
     * @param expiredAt
     */
    public SsoTokenModel(String username, String password, String type, long expiredAt) {
        super();
        this.username = username;
        this.password = password;
        this.type = type;
        this.expiredAt = expiredAt;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the expiredAt
     */
    public long getExpiredAt() {
        return expiredAt;
    }

    /**
     * @param expiredAt the expiredAt to set
     */
    public void setExpiredAt(long expiredAt) {
        this.expiredAt = expiredAt;
    }

}
