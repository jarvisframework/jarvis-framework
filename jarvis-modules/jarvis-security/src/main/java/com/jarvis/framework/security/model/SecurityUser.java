package com.jarvis.framework.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jarvis.framework.core.security.DefaultUser;
import com.jarvis.framework.core.security.LoginUser;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月28日
 */
public class SecurityUser extends DefaultUser implements LoginUser, UserDetails, CredentialsContainer {

    /**
     *
     */
    private static final long serialVersionUID = 6349536266597830740L;

    private final List<GrantedAuthority> authorities = new ArrayList<>();

    /**
     *
     * @see org.springframework.security.core.CredentialsContainer#eraseCredentials()
     */
    @Override
    public void eraseCredentials() {
        setPassword(null);
    }

    /**
     *
     * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
     */
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     *
     */
    public void addAuthorities(GrantedAuthority... authorities) {
        Stream.of(authorities).forEach(this.authorities::add);
    }

    /**
     *
     */
    public void addAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities.addAll(authorities);
    }

    /**
     *
     * @see com.jarvis.framework.core.security.DefaultUser#getPassword()
     */
    @JsonIgnore
    @Override
    public String getPassword() {
        return super.getPassword();
    }

    /**
     *
     * @see com.jarvis.framework.core.security.DefaultUser#isEnabled()
     */
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }

    /**
     *
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return super.isAccountNonExpired();
    }

    /**
     *
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return super.isAccountNonLocked();
    }

    /**
     *
     * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
     */
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return super.isCredentialsNonExpired();
    }

}
