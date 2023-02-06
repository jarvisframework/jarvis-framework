package com.jarvis.framework.security.authentication;

import com.jarvis.framework.security.authentication.config.BadCreadentialsProperties;
import com.jarvis.framework.security.service.AccountLockService;
import com.jarvis.framework.security.service.BadCreadentialsService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 针对BadCredentialsException进行限制登录功能
 *
 * @author qiucs
 * @version 1.0.0 2021年4月27日
 */
public class LimitBadCreadentialsDaoAuthenticationProvider extends DaoAuthenticationProvider {

    private BadCreadentialsService badCreadentialsService = new NullBadCreadentialsService();

    private BadCreadentialsProperties badCreadentialsProperties;

    private AccountLockService accountLockService = new NullAccountLockService();

    /**
     *
     * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!badCreadentialsProperties.isEnabled() || authentication.getPrincipal() == null) {
            final Authentication authenticate = super.authenticate(authentication);
            return authenticate;
        }
        final String username = authentication.getName();
        try {
            badCreadentialsService.checkAccountNonLocked(username);
            final Authentication authenticate = super.authenticate(authentication);
            badCreadentialsService.releaseAccountLocked(username);
            return authenticate;
        } catch (final BadCredentialsException e) {
            final int totalCount = badCreadentialsProperties.getCount();
            final int errorCount = badCreadentialsService.increaseErrorCount(username);
            if (errorCount >= totalCount) {
                final int timeout = badCreadentialsProperties.getTimeout();
                accountLockService.lock(username, totalCount, timeout);
                if (timeout > 0) {
                    throw new LockedException(
                            String.format("密码已连续出错[%d]次，用户帐号已被锁定，请于[%s]分钟后再登录！", totalCount, timeout));
                } else {
                    throw new LockedException(
                            String.format("密码已连续出错[%d]次，用户帐号已被锁定，请联系管理员！", totalCount));
                }
            } else {
                throw new BadCredentialsException(
                        String.format("用户密码错误，共[%s]次机会，还有[%s]次登录机会！", totalCount, totalCount - errorCount), e);
            }
        }
    }

    /*@Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
        UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        final long start = System.currentTimeMillis();
        super.additionalAuthenticationChecks(userDetails, authentication);
        if (logger.isInfoEnabled()) {
            logger
                .info(String.format("登录[additionalAuthenticationChecks]处理共耗时：%s ms",
                    (System.currentTimeMillis() - start)));
        }
    }*/

    public void setBadCreadentialsService(BadCreadentialsService badCreadentialsService) {
        this.badCreadentialsService = badCreadentialsService;
    }

    public void setBadCreadentialsProperties(BadCreadentialsProperties badCreadentialsProperties) {
        this.badCreadentialsProperties = badCreadentialsProperties;
    }

    /**
     * @param accountLockService the accountLockService to set
     */
    public void setAccountLockService(AccountLockService accountLockService) {
        this.accountLockService = accountLockService;
    }

    private class NullBadCreadentialsService implements BadCreadentialsService {

        /**
         *
         * @see com.jarvis.framework.security.service.BadCreadentialsService#checkAccountNonLocked(java.lang.String)
         */
        @Override
        public void checkAccountNonLocked(String username) {
        }

        /**
         *
         * @see com.jarvis.framework.security.service.BadCreadentialsService#increaseErrorCount(java.lang.String)
         */
        @Override
        public int increaseErrorCount(String username) {
            return 0;
        }

        /**
         *
         * @see com.jarvis.framework.security.service.BadCreadentialsService#releaseAccountLocked(java.lang.String)
         */
        @Override
        public void releaseAccountLocked(String username) {

        }

    }

    private class NullAccountLockService implements AccountLockService {

        /**
         *
         * @see com.jarvis.framework.security.service.AccountLockService#lock(java.lang.String, int, int)
         */
        @Override
        public void lock(String username, int count, int timeout) {
            // TODO Auto-generated method stub

        }

    }
}
