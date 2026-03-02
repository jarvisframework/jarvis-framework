package com.jarvis.framework.redis.cache;

import java.io.Serializable;

/**
 * 缓存消息
 *
 * @author Doug Wang
 * @version 1.0.0 2023年2月22日
 */
public class CacheMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cacheName;

    private Object key;

    private String sourceId;

    public CacheMessage() {
    }

    public CacheMessage(String cacheName, Object key, String sourceId) {
        this.cacheName = cacheName;
        this.key = key;
        this.sourceId = sourceId;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
}
