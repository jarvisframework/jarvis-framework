package com.jarvis.framework.security.authentication.idcard;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

public class IdcardAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 540L;
    private final Object principal;
    private final IdcardModel idcardModel;

    public IdcardAuthenticationToken(Object principal, IdcardModel idcardModel) {
        super((Collection)null);
        this.principal = principal;
        this.idcardModel = idcardModel;
        this.setAuthenticated(false);
    }

    public IdcardAuthenticationToken(Object principal, IdcardModel idcardModel, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.idcardModel = idcardModel;
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

    public IdcardModel getIdcardMode() {
        return this.idcardModel;
    }
}
