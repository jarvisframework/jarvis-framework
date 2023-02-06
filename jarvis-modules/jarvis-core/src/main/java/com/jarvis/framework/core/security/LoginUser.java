package com.jarvis.framework.core.security;

import java.io.Serializable;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月28日
 */
public interface LoginUser extends Serializable {

    /**
     * id
     *
     * @return
     */
    Object getId();

    /**
     * 租户ID
     *
     * @return
     */
    Object getTenantId();

    /**
     * 显示名称
     *
     * @return
     */
    String getShowName();

    /**
     * 登录名
     *
     * @return
     */
    String getUsername();

    /**
     * 密码
     *
     * @return
     */
    String getPassword();

    /**
     * 用户详细信息
     *
     * @return
     */
    Object getUserDetails();

    /**
     * 是否可用
     *
     * @return
     */
    boolean isEnabled();

    /**
     * 帐号是否过期
     *
     * @return
     */
    boolean isAccountNonExpired();

    /**
     * 帐号不锁定.
     *
     * @return <code>true</code> if the user is not locked,
     *         <code>false</code> otherwise
     */
    boolean isAccountNonLocked();

    /**
     * 密码是否过期
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     *         <code>false</code> if no longer valid (ie expired)
     */
    boolean isCredentialsNonExpired();
}
