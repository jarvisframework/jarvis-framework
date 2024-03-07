package com.jarvis.framework.redis.util;

import com.jarvis.framework.constant.SymbolConstant;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年6月29日
 */
public class RedisKeyUtil {

    /**
     * 拼接缓存key
     *
     * @param cacheName 缓存名称
     * @param key key
     * @return String
     */
    public static String cacheKey(String cacheName, String key) {
        return cacheName + SymbolConstant.COLON + key;
    }
}
