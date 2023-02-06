package com.jarvis.framework.security.authentication.sso;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年3月22日
 */
public class SsoAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;

    private final SsoTokenModel ssoTokenModel;

    /**
     * This constructor can be safely used by any code that wishes to create a
     * <code>UsernamePasswordAuthenticationToken</code>, as the {@link #isAuthenticated()}
     * will return <code>false</code>.
     *
     * @param principal
     * @param ssoTokenModel
     */
    public SsoAuthenticationToken(Object principal, SsoTokenModel ssoTokenModel) {
        super(null);
        this.principal = principal;
        this.ssoTokenModel = ssoTokenModel;
        setAuthenticated(false);
    }

    /**
     * This constructor should only be used by <code>AuthenticationManager</code> or
     * <code>AuthenticationProvider</code> implementations that are satisfied with
     * producing a trusted (i.e. {@link #isAuthenticated()} = <code>true</code>)
     * authentication token.
     *
     * @param principal
     * @param ssoTokenModel
     * @param authorities
     */
    public SsoAuthenticationToken(Object principal, SsoTokenModel ssoTokenModel,
                                  Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.ssoTokenModel = ssoTokenModel;
        super.setAuthenticated(true); // must use super, as we override
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

    /**
     * @return the tokenModel
     */
    public SsoTokenModel getSsoTokenModel() {
        return ssoTokenModel;
    }
}
