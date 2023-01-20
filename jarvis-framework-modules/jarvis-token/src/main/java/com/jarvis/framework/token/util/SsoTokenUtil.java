package com.jarvis.framework.token.util;

import com.jarvis.framework.cypto.util.AESUtil;

public abstract class SsoTokenUtil {
    public static final String DELIM = ":";
    public static final String SCHEME = "{SSO}";
    public static final String HEADER = "X-Token";
    public static final String TYPE_ONCE = "1";
    public static final String TYPE_UNLIMITED = "0";
    private static final long TIME_OUT = 30L;

    public SsoTokenUtil() {
    }

    public static String encodeUnlimited(String username, String password, long timeout) {
        if (username.indexOf(":") <= -1 && password.indexOf(":") <= -1) {
            return digestUnlimited(username, password, timeout);
        } else {
            throw new RuntimeException("用户名和密码不能包含英文冒号（:）字符");
        }
    }

    public static String encode(String username, String password, long timeout) {
        if (username.indexOf(":") <= -1 && password.indexOf(":") <= -1) {
            return digest(username, password, timeout);
        } else {
            throw new RuntimeException("用户名和密码不能包含英文冒号（:）字符");
        }
    }

    public static String encode(String username, String password) {
        if (username.indexOf(":") <= -1 && password.indexOf(":") <= -1) {
            return digest(username, password, 30L);
        } else {
            throw new RuntimeException("用户名和密码不能包含英文冒号（:）字符");
        }
    }

    private static String digestUnlimited(String username, String password, long timeout) {
        long expiredAt = System.currentTimeMillis() + 1800000L;
        String data = username + ":" + "0" + ":" + password + ":" + expiredAt;
        return "{SSO}" + AESUtil.encrypt(data);
    }

    private static String digest(String username, String password, long timeout) {
        long expiredAt = System.currentTimeMillis() + 1800000L;
        String data = username + ":" + "1" + ":" + password + ":" + expiredAt + ":" + Math.random();
        return "{SSO}" + AESUtil.encrypt(data);
    }

    public static String decode(String token) {
        return AESUtil.decrypt(token);
    }
}
