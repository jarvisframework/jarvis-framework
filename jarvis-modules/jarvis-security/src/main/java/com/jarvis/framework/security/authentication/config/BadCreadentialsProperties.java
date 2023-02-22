package com.jarvis.framework.security.authentication.config;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月27日
 */
public class BadCreadentialsProperties {

    private boolean enabled;

    /**
     * 出错次数，默认5次
     */
    private int count = 5;

    /**
     * 锁定时间（单位：分钟），默认30分钟
     */
    private int timeout = 30;

    /**
     * 是否通过更改用户状态来锁定
     */
    private boolean lockUser = true;

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return the timeout
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * @param timeout the timeout to set
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * @return the lockUser
     */
    public boolean isLockUser() {
        return lockUser;
    }

    /**
     * @param lockUser the lockUser to set
     */
    public void setLockUser(boolean lockUser) {
        this.lockUser = lockUser;
    }

}
