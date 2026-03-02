package com.jarvis.framework.redis.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * 多级缓存管理器
 *
 * @author Doug Wang
 * @version 1.0.0 2023年2月22日
 */
public class MultiLevelCacheManager implements CacheManager {

    private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>();
    private final RedisCacheManager redisCacheManager;
    private final MultiLevelCacheProperties properties;
    private final RedisTemplate<String, Object> redisTemplate;
    
    // 使用默认的主题
    public static final String DEFAULT_TOPIC = "jarvis:cache:topic";
    
    // 缓存实例ID
    private final String sourceId = UUID.randomUUID().toString();

    public MultiLevelCacheManager(RedisCacheManager redisCacheManager,
                                  MultiLevelCacheProperties properties,
                                  RedisTemplate<String, Object> redisTemplate) {
        this.redisCacheManager = redisCacheManager;
        this.properties = properties;
        this.redisTemplate = redisTemplate;
    }

    public String getSourceId() {
        return sourceId;
    }

    @Override
    public Cache getCache(String name) {
        Cache cache = cacheMap.get(name);
        if (cache != null) {
            return cache;
        }

        synchronized (cacheMap) {
            cache = cacheMap.get(name);
            if (cache == null) {
                cache = createMultiLevelCache(name);
                cacheMap.put(name, cache);
            }
            return cache;
        }
    }

    private Cache createMultiLevelCache(String name) {
        Cache redisCache = redisCacheManager.getCache(name);
        
        com.github.benmanes.caffeine.cache.Cache<Object, Object> caffeineCache = Caffeine.newBuilder()
                .initialCapacity(properties.getCaffeine().getInitialCapacity())
                .maximumSize(properties.getCaffeine().getMaximumSize())
                .expireAfterWrite(properties.getCaffeine().getExpireAfterWrite(), TimeUnit.SECONDS)
                .build();

        return new MultiLevelCache(name, caffeineCache, redisCache, redisTemplate, DEFAULT_TOPIC, true, sourceId);
    }

    @Override
    public Collection<String> getCacheNames() {
        return redisCacheManager.getCacheNames();
    }
    
    /**
     * 清理本地缓存
     * @param name 缓存名称
     * @param key 缓存Key
     */
    public void clearLocal(String name, Object key) {
        Cache cache = cacheMap.get(name);
        if (cache instanceof MultiLevelCache) {
            ((MultiLevelCache) cache).clearLocal(key);
        }
    }
}
