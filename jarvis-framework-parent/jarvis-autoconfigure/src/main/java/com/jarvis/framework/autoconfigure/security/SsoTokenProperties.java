package com.jarvis.framework.autoconfigure.security;

import com.jarvis.framework.security.authentication.sso.SsoTokenUser;

import java.util.List;

public class SsoTokenProperties {
    private boolean enabled;
    private List<SsoTokenUser> users;

    public boolean isEnabled() {
        return a.enabled;
    }

    public SsoTokenProperties() {
    }

    public void setUsers(List<SsoTokenUser> a) {
        a.users = a;
    }

    public void setEnabled(boolean a) {
        a.enabled = a;
    }

    public List<SsoTokenUser> getUsers() {
        return a.users;
    }
}
