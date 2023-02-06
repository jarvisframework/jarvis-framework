package com.jarvis.framework.security.authentication.sso.service;

import com.jarvis.framework.security.authentication.sso.SsoAuthenticationToken;
import com.jarvis.framework.security.authentication.sso.SsoDetailsService;
import com.jarvis.framework.security.authentication.sso.SsoTokenModel;
import com.jarvis.framework.security.authentication.sso.SsoTokenUser;
import com.jarvis.framework.security.model.SecurityUser;
import com.jarvis.framework.token.util.SsoTokenUtil;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年3月18日
 */
public class InMemorySsoDetailsService implements SsoDetailsService {

    private final Map<String, SsoTokenUser> users = new HashMap<>();

    private final Map<String, Long> tokenCache = new ConcurrentHashMap<>();

    public InMemorySsoDetailsService(List<SsoTokenUser> ssoTokenUsers) {
        ssoTokenUsers.stream().forEach(u -> {
            users.put(u.getUsername(), u);
        });
    }

    /**
     *
     * @see com.jarvis.framework.security.authentication.sso.SsoDetailsService#loadSecurityUser(com.jarvis.framework.security.authentication.sso.SsoAuthenticationToken)
     */
    @Override
    public SecurityUser loadSecurityUser(SsoAuthenticationToken authenticationToken) {
        final SsoTokenModel ssoTokenModel = authenticationToken.getSsoTokenModel();
        final String username = ssoTokenModel.getUsername();
        final String token = (String) authenticationToken.getPrincipal();
        final long currentTimeMillis = System.currentTimeMillis();

        final boolean isOnce = SsoTokenUtil.TYPE_ONCE.equals(ssoTokenModel.getType());

        if (isOnce) {
            final Long value = tokenCache.get(token);
            removeExpired(currentTimeMillis);
            if (null != value && value.longValue() > 0) {
                throw new BadCredentialsException("token已失效");
            }
        }

        final long expiredAt = ssoTokenModel.getExpiredAt();
        if (currentTimeMillis > expiredAt) {
            throw new BadCredentialsException("token已失效");
        }

        final SsoTokenUser ssoTokenUser = users.get(username);

        if (null == ssoTokenUser) {
            throw new BadCredentialsException("非法token");
        }

        if (!ssoTokenUser.getPassword().equals(ssoTokenModel.getPassword())) {
            throw new BadCredentialsException("非法token");
        }

        if (isOnce) {
            tokenCache.put(token, expiredAt);
        }

        return toSecurityUser(ssoTokenUser);
    }

    private void removeExpired(long currentTimeMillis) {
        final Iterator<Entry<String, Long>> it = tokenCache.entrySet().iterator();
        while (it.hasNext()) {
            final Entry<String, Long> next = it.next();
            if (next.getValue() < currentTimeMillis) {
                it.remove();
            }
        }
    }

    private SecurityUser toSecurityUser(SsoTokenUser user) {
        final SecurityUser securityUser = new SecurityUser();
        securityUser.setId(user.getId());
        securityUser.setUsername(user.getUsername());
        securityUser.setShowName(user.getUsername());
        securityUser.setPassword(user.getPassword());
        return securityUser;
    }

}
