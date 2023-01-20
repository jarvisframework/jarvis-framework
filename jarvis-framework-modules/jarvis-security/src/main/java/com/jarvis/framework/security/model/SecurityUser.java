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

public class SecurityUser extends DefaultUser implements LoginUser, UserDetails, CredentialsContainer {
    private static final long serialVersionUID = 6349536266597830740L;
    private final List<GrantedAuthority> authorities = new ArrayList();

    public SecurityUser() {
    }

    public void eraseCredentials() {
        this.setPassword((String)null);
    }

    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void addAuthorities(GrantedAuthority... authorities) {
        Stream var10000 = Stream.of(authorities);
        List var10001 = this.authorities;
        var10000.forEach(var10001::add);
    }

    public void addAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities.addAll(authorities);
    }

    @JsonIgnore
    public String getPassword() {
        return super.getPassword();
    }

    @JsonIgnore
    public boolean isEnabled() {
        return super.isEnabled();
    }

    @JsonIgnore
    public boolean isAccountNonExpired() {
        return super.isAccountNonExpired();
    }

    @JsonIgnore
    public boolean isAccountNonLocked() {
        return super.isAccountNonLocked();
    }

    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return super.isCredentialsNonExpired();
    }
}
