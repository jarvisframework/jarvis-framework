package com.jarvis.framework.security.authentication;

import com.jarvis.framework.security.authentication.config.BadCreadentialsProperties;
import com.jarvis.framework.security.service.BadCreadentialsService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class LimitBadCreadentialsDaoAuthenticationProvider extends DaoAuthenticationProvider {
    private BadCreadentialsService badCreadentialsService = new LimitBadCreadentialsDaoAuthenticationProvider.NullBadCreadentialsService();
    private BadCreadentialsProperties badCreadentialsProperties;

    public LimitBadCreadentialsDaoAuthenticationProvider() {
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (this.badCreadentialsProperties.isEnabled() && authentication.getPrincipal() != null) {
            String username = authentication.getName();

            try {
                this.badCreadentialsService.checkAccountNonLocked(username);
                Authentication authenticate = super.authenticate(authentication);
                this.badCreadentialsService.releaseAccountLocked(username);
                return authenticate;
            } catch (BadCredentialsException var6) {
                int totalCount = this.badCreadentialsProperties.getCount();
                int errorCount = this.badCreadentialsService.increaseErrorCount(username);
                throw new BadCredentialsException(String.format("用户密码错误，共[%s]次机会，还有[%s]次登录机会！", totalCount, totalCount - errorCount), var6);
            }
        } else {
            Authentication authenticate = super.authenticate(authentication);
            return authenticate;
        }
    }

    public void setBadCreadentialsService(BadCreadentialsService badCreadentialsService) {
        this.badCreadentialsService = badCreadentialsService;
    }

    public void setBadCreadentialsProperties(BadCreadentialsProperties badCreadentialsProperties) {
        this.badCreadentialsProperties = badCreadentialsProperties;
    }

    private class NullBadCreadentialsService implements BadCreadentialsService {
        private NullBadCreadentialsService() {
        }

        public void checkAccountNonLocked(String username) {
        }

        public int increaseErrorCount(String username) {
            return 0;
        }

        public void releaseAccountLocked(String username) {
        }
    }
}
