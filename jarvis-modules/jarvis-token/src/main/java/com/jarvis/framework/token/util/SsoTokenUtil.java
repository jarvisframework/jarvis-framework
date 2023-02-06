package com.jarvis.framework.token.util;

import com.jarvis.framework.cypto.util.AESUtil;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年3月18日
 */
public abstract class SsoTokenUtil {

    public static final String DELIM = ":";

    public static final String SCHEME = "{SSO}";

    public static final String HEADER = "X-Token";

    public static final String TYPE_ONCE = "1";

    public static final String TYPE_UNLIMITED = "0";

    private static final long TIME_OUT = 30;

    /**
     * 生成token(使用不受限制)
     *
     * @param username 用户名
     * @param password 密码
     * @param timeout 过期时间（单位：秒）
     * @return
     */
    public static String encodeUnlimited(String username, String password, long timeout) {
        if (username.indexOf(DELIM) > -1 || password.indexOf(DELIM) > -1) {
            throw new RuntimeException("用户名和密码不能包含英文冒号（:）字符");
        }
        return digestUnlimited(username, password, timeout);
    }

    /**
     * 生成token(只能使用一次)
     *
     * @param username 用户名
     * @param password 密码
     * @param timeout 过期时间（单位：秒）
     * @return
     */
    public static String encode(String username, String password, long timeout) {
        if (username.indexOf(DELIM) > -1 || password.indexOf(DELIM) > -1) {
            throw new RuntimeException("用户名和密码不能包含英文冒号（:）字符");
        }
        return digest(username, password, timeout);
    }

    /**
     * 生成token（默认30秒）
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public static String encode(String username, String password) {
        if (username.indexOf(DELIM) > -1 || password.indexOf(DELIM) > -1) {
            throw new RuntimeException("用户名和密码不能包含英文冒号（:）字符");
        }
        return digest(username, password, TIME_OUT);
    }

    private static String digestUnlimited(String username, String password, long timeout) {
        final long expiredAt = System.currentTimeMillis() + 30 * 60 * 1000;
        final String data = username + DELIM + TYPE_UNLIMITED + DELIM + password + DELIM + expiredAt;
        return SCHEME + AESUtil.encrypt(data);
    }

    private static String digest(String username, String password, long timeout) {
        final long expiredAt = System.currentTimeMillis() + 30 * 60 * 1000;
        final String data = username + DELIM + TYPE_ONCE + DELIM + password + DELIM + expiredAt + DELIM + Math.random();
        return SCHEME + AESUtil.encrypt(data);
    }

    /**
     * 解析token
     *
     * @param token token
     * @return String
     */
    public static String decode(String token) {
        return AESUtil.decrypt(token);
    }
}
