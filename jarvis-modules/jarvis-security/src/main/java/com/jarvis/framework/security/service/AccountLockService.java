package com.jarvis.framework.security.service;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2023年1月17日
 */
public interface AccountLockService {

    /**
     * 锁定用户
     *
     * @param username 用户名
     * @param count 用户名
     * @param timeout 用户名
     */
    void lock(String username, int count, int timeout);

}
