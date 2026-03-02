package com.jarvis.framework.redis.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Doug Wang
 * @version 1.0.0 2023年2月22日
 */
@ConfigurationProperties(prefix = "jarvis.cache.multi")
public class MultiLevelCacheProperties {

    /**
     * 是否开启多级缓存
     */
    private boolean enabled = false;

    /**
     * Caffeine缓存配置
     */
    private Caffeine caffeine = new Caffeine();

    /**
     * Redis缓存配置
     */
    private Redis redis = new Redis();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Caffeine getCaffeine() {
        return caffeine;
    }

    public void setCaffeine(Caffeine caffeine) {
        this.caffeine = caffeine;
    }

    public Redis getRedis() {
        return redis;
    }

    public void setRedis(Redis redis) {
        this.redis = redis;
    }

    public static class Caffeine {
        /**
         * 初始容量
         */
        private int initialCapacity = 10;

        /**
         * 最大容量
         */
        private long maximumSize = 500;

        /**
         * 过期时间（秒），默认60秒
         */
        private long expireAfterWrite = 60;

        public int getInitialCapacity() {
            return initialCapacity;
        }

        public void setInitialCapacity(int initialCapacity) {
            this.initialCapacity = initialCapacity;
        }

        public long getMaximumSize() {
            return maximumSize;
        }

        public void setMaximumSize(long maximumSize) {
            this.maximumSize = maximumSize;
        }

        public long getExpireAfterWrite() {
            return expireAfterWrite;
        }

        public void setExpireAfterWrite(long expireAfterWrite) {
            this.expireAfterWrite = expireAfterWrite;
        }
    }

    public static class Redis {
        /**
         * 全局过期时间（秒），默认1小时
         */
        private long defaultExpiration = 3600;

        /**
         * 针对不同缓存名称设置不同的过期时间
         */
        private Map<String, Long> expires = new HashMap<>();

        public long getDefaultExpiration() {
            return defaultExpiration;
        }

        public void setDefaultExpiration(long defaultExpiration) {
            this.defaultExpiration = defaultExpiration;
        }

        public Map<String, Long> getExpires() {
            return expires;
        }

        public void setExpires(Map<String, Long> expires) {
            this.expires = expires;
        }
    }
}
