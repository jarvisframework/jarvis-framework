package com.jarvis.framework.core.security;

import java.io.Serializable;

public interface LoginUser extends Serializable {
    Object getId();

    Object getTenantId();

    String getShowName();

    String getUsername();

    String getPassword();

    Object getUserDetails();

    boolean isEnabled();

    boolean isAccountNonExpired();

    boolean isAccountNonLocked();

    boolean isCredentialsNonExpired();
}
