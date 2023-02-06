package com.jarvis.framework.security.service;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月27日
 */
public interface BadCreadentialsService {

    void checkAccountNonLocked(String username);

    int increaseErrorCount(String username);

    void releaseAccountLocked(String username);
}
