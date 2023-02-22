package com.jarvis.framework.security.password;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年8月20日
 */
public interface DelegatePasswordEncoder {

    /**
     * 密码解析器ID，预置ID如：bcrypt/scrypt/MD5/MD4/ldap/noop/pbkdf2/SHA-1/SHA-256/sha256/argon2
     * 如果ID返回预置的ID，则是替换预置的密码解析器
     *
     * 也可自定义，如国产：SM1/SM2/SM3/SM4
     *
     * @return
     */
    String encoderId();

    /**
     * 密码解析器
     *
     * @return
     */
    PasswordEncoder passwordEncoder();

}
