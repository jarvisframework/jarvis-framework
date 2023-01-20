package com.jarvis.framework.core.security;

/**
 * <p>默认用户类</p>
 *
 * @author Doug Wang
 * @since 1.8, 2022-12-17 14:46:04
 */
public class DefaultUser implements LoginUser {
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

    public Object getId() {
        return this.id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(Object tenantId) {
        this.tenantId = tenantId;
    }

    public String getShowName() {
        return this.showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
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

    public Object getUserDetails() {
        return this.userDetails;
    }

    public void setUserDetails(Object userDetails) {
        this.userDetails = userDetails;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }
}
