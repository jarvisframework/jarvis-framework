package com.jarvis.framework.core.security;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月28日
 */
public class DefaultUser implements LoginUser {

    /**
     *
     */
    private static final long serialVersionUID = 4907126380631597316L;

    private Object id;

    private Object tenantId = 1L;

    private String showName;

    private String username;

    private transient String password;

    private Object userDetails;

    private transient boolean enabled = true;

    private transient boolean accountNonExpired = true;

    private transient boolean accountNonLocked = true;

    private transient boolean credentialsNonExpired = true;

    /**
     * @return the id
     */
    @Override
    public Object getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Object id) {
        this.id = id;
    }

    /**
     * @return the tenantId
     */
    @Override
    public Object getTenantId() {
        return tenantId;
    }

    /**
     * @param tenantId the tenantId to set
     */
    public void setTenantId(Object tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * @return the showName
     */
    @Override
    public String getShowName() {
        return showName;
    }

    /**
     * @param showName the showName to set
     */
    public void setShowName(String showName) {
        this.showName = showName;
    }

    /**
     * @return the username
     */
    @Override
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
    @Override
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
     * @return the userDetails
     */
    @Override
    public Object getUserDetails() {
        return userDetails;
    }

    /**
     * @param userDetails the userDetails to set
     */
    public void setUserDetails(Object userDetails) {
        this.userDetails = userDetails;
    }

    /**
     * @return the enabled
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the accountNonExpired
     */
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    /**
     * @param accountNonExpired the accountNonExpired to set
     */
    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    /**
     * @return the accountNonLocked
     */
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    /**
     * @param accountNonLocked the accountNonLocked to set
     */
    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    /**
     * @return the credentialsNonExpired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    /**
     * @param credentialsNonExpired the credentialsNonExpired to set
     */
    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LoginUser)) {
            return false;
        }
        final DefaultUser test = (DefaultUser) obj;
        if (!this.id.equals(test.id)) {
            return false;
        }
        if (!this.username.equals(test.username)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int code = 31;
        if (this.id != null) {
            code ^= this.id.hashCode();
        }
        if (this.username != null) {
            code ^= this.username.hashCode();
        }
        return code;
    }

}
