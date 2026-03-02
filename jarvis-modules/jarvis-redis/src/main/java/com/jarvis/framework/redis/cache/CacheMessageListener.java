package com.jarvis.framework.redis.cache;

import com.jarvis.framework.redis.core.StringObjectRedisTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 缓存消息监听器
 *
 * @author Doug Wang
 * @version 1.0.0 2023年2月22日
 */
public class CacheMessageListener implements MessageListener {

    private static final Logger log = LoggerFactory.getLogger(CacheMessageListener.class);

    private final StringObjectRedisTemplate redisTemplate;

    private final MultiLevelCacheManager cacheManager;

    public CacheMessageListener(StringObjectRedisTemplate redisTemplate, MultiLevelCacheManager cacheManager) {
        this.redisTemplate = redisTemplate;
        this.cacheManager = cacheManager;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            RedisSerializer<Object> valueSerializer = (RedisSerializer<Object>) redisTemplate.getValueSerializer();
            CacheMessage cacheMessage = (CacheMessage) valueSerializer.deserialize(message.getBody());
            if (cacheMessage != null) {
                log.debug("Receive cache message: cacheName={}, key={}", cacheMessage.getCacheName(), cacheMessage.getKey());
                cacheManager.clearLocal(cacheMessage.getCacheName(), cacheMessage.getKey());
            }
        } catch (Exception e) {
            log.error("Failed to handle cache message", e);
        }
    }
}
