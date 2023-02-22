package com.jarvis.framework.autoconfigure.security;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年2月3日
 */
public class AuthorityUrl {

    /**
     * url地址，如 /menu/**
     */
    private String url;

    /**
     * 权限
     */
    private String[] authorities;

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the authorities
     */
    public String[] getAuthorities() {
        return authorities;
    }

    /**
     * @param authorities the authorities to set
     */
    public void setAuthorities(String[] authorities) {
        this.authorities = authorities;
    }

}
