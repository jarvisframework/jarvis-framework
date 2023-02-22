package com.jarvis.framework.autoconfigure.security;

import com.jarvis.framework.security.authentication.sso.SsoTokenUser;

import java.util.List;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年3月18日
 */
public class SsoTokenProperties {

    private boolean enabled;

    private List<SsoTokenUser> users;

    /**
     * @return the enabled
     */
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
     * @return the users
     */
    public List<SsoTokenUser> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<SsoTokenUser> users) {
        this.users = users;
    }

}
