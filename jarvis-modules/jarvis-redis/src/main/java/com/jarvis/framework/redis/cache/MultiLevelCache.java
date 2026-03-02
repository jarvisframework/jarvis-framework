package com.jarvis.framework.redis.cache;

import com.github.benmanes.caffeine.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.util.concurrent.Callable;

/**
 * 多级缓存实现
 *
 * @author Doug Wang
 * @version 1.0.0 2023年2月22日
 */
public class MultiLevelCache extends AbstractValueAdaptingCache {

    private static final Logger log = LoggerFactory.getLogger(MultiLevelCache.class);

    private final String name;
    private final Cache<Object, Object> caffeineCache;
    private final org.springframework.cache.Cache redisCache;
    private final RedisTemplate<String, Object> redisTemplate;
    private final String topic;
    private final String sourceId;

    public MultiLevelCache(String name, Cache<Object, Object> caffeineCache, org.springframework.cache.Cache redisCache,
                           RedisTemplate<String, Object> redisTemplate, String topic, boolean allowNullValues, String sourceId) {
        super(allowNullValues);
        this.name = name;
        this.caffeineCache = caffeineCache;
        this.redisCache = redisCache;
        this.redisTemplate = redisTemplate;
        this.topic = topic;
        this.sourceId = sourceId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this;
    }

    @Override
    protected Object lookup(Object key) {
        // 1. 从 Caffeine 中获取
        Object value = caffeineCache.getIfPresent(key);
        if (value != null) {
            log.debug("Get cache from caffeine, cacheName={}, key={}", name, key);
            return value;
        }

        // 2. 从 Redis 中获取
        value = redisCache.get(key, Object.class);
        if (value != null) {
            log.debug("Get cache from redis, cacheName={}, key={}", name, key);
            // 放入 Caffeine
            caffeineCache.put(key, value);
        }
        return value;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        Object value = lookup(key);
        if (value != null) {
            return (T) value;
        }

        // 双检锁
        synchronized (key) {
            value = lookup(key);
            if (value != null) {
                return (T) value;
            }
            try {
                value = valueLoader.call();
            } catch (Exception e) {
                throw new ValueRetrievalException(key, valueLoader, e);
            }
            put(key, value);
            return (T) value;
        }
    }

    @Override
    public void put(Object key, Object value) {
        // 1. 放入 Redis
        redisCache.put(key, value);
        // 2. 放入 Caffeine
        caffeineCache.put(key, value);
        // 3. 发送消息通知其他节点清除 Caffeine 缓存
        publish(key);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        // 1. Redis putIfAbsent
        ValueWrapper wrapper = redisCache.putIfAbsent(key, value);
        
        // 如果 Redis 中已存在，则返回存在的值
        if (wrapper != null) {
            // 同时也放入 Caffeine（保持一致性）
            caffeineCache.put(key, wrapper.get());
            return wrapper;
        }

        // 如果 Redis 中不存在（即 put 成功），则放入 Caffeine 并通知
        caffeineCache.put(key, value);
        publish(key);
        return toValueWrapper(value);
    }

    @Override
    public void evict(Object key) {
        // 1. 清除 Redis
        redisCache.evict(key);
        // 2. 清除 Caffeine
        caffeineCache.invalidate(key);
        // 3. 发送消息
        publish(key);
    }

    @Override
    public void clear() {
        // 1. 清除 Redis
        redisCache.clear();
        // 2. 清除 Caffeine
        caffeineCache.invalidateAll();
        // 3. 发送消息
        publish(null);
    }

    /**
     * 清除本地缓存（接收到消息时调用）
     *
     * @param key 缓存键
     */
    public void clearLocal(Object key) {
        if (key == null) {
            caffeineCache.invalidateAll();
        } else {
            caffeineCache.invalidate(key);
        }
    }

    private void publish(Object key) {
        redisTemplate.convertAndSend(topic, new CacheMessage(name, key, sourceId));
    }
}
