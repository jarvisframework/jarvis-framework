package com.jarvis.framework.security.authentication.sso;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

public class SsoAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 540L;
    private final Object principal;
    private final SsoTokenModel ssoTokenModel;

    public SsoAuthenticationToken(Object principal, SsoTokenModel ssoTokenModel) {
        super((Collection)null);
        this.principal = principal;
        this.ssoTokenModel = ssoTokenModel;
        this.setAuthenticated(false);
    }

    public SsoAuthenticationToken(Object principal, SsoTokenModel ssoTokenModel, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.ssoTokenModel = ssoTokenModel;
        super.setAuthenticated(true);
    }

    public Object getCredentials() {
        return null;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    public void eraseCredentials() {
        super.eraseCredentials();
    }

    public SsoTokenModel getSsoTokenModel() {
        return this.ssoTokenModel;
    }
}
