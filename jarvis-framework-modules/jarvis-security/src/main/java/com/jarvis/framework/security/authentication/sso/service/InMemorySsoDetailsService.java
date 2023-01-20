package com.jarvis.framework.security.authentication.sso.service;

import com.jarvis.framework.security.authentication.sso.SsoAuthenticationToken;
import com.jarvis.framework.security.authentication.sso.SsoDetailsService;
import com.jarvis.framework.security.authentication.sso.SsoTokenModel;
import com.jarvis.framework.security.authentication.sso.SsoTokenUser;
import com.jarvis.framework.security.model.SecurityUser;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class InMemorySsoDetailsService implements SsoDetailsService {
    private final Map<String, SsoTokenUser> users = new HashMap();
    private final Map<String, Long> tokenCache = new ConcurrentHashMap();

    public InMemorySsoDetailsService(List<SsoTokenUser> ssoTokenUsers) {
        ssoTokenUsers.stream().forEach((u) -> {
            this.users.put(u.getUsername(), u);
        });
    }

    public SecurityUser loadSecurityUser(SsoAuthenticationToken authenticationToken) {
        SsoTokenModel ssoTokenModel = authenticationToken.getSsoTokenModel();
        String username = ssoTokenModel.getUsername();
        String token = (String)authenticationToken.getPrincipal();
        long currentTimeMillis = System.currentTimeMillis();
        boolean isOnce = "1".equals(ssoTokenModel.getType());
        if (isOnce) {
            Long value = (Long)this.tokenCache.get(token);
            this.removeExpired(currentTimeMillis);
            if (null != value && value > 0L) {
                throw new BadCredentialsException("token已失效");
            }
        }

        long expiredAt = ssoTokenModel.getExpiredAt();
        if (currentTimeMillis > expiredAt) {
            throw new BadCredentialsException("token已失效");
        } else {
            SsoTokenUser ssoTokenUser = (SsoTokenUser)this.users.get(username);
            if (null == ssoTokenUser) {
                throw new BadCredentialsException("非法token");
            } else if (!ssoTokenUser.getPassword().equals(ssoTokenModel.getPassword())) {
                throw new BadCredentialsException("非法token");
            } else {
                if (isOnce) {
                    this.tokenCache.put(token, expiredAt);
                }

                return this.toSecurityUser(ssoTokenUser);
            }
        }
    }

    private void removeExpired(long currentTimeMillis) {
        Iterator it = this.tokenCache.entrySet().iterator();

        while(it.hasNext()) {
            Entry<String, Long> next = (Entry)it.next();
            if ((Long)next.getValue() < currentTimeMillis) {
                it.remove();
            }
        }

    }

    private SecurityUser toSecurityUser(SsoTokenUser user) {
        SecurityUser securityUser = new SecurityUser();
        securityUser.setId(user.getId());
        securityUser.setUsername(user.getUsername());
        securityUser.setShowName(user.getUsername());
        securityUser.setPassword(user.getPassword());
        return securityUser;
    }
}
