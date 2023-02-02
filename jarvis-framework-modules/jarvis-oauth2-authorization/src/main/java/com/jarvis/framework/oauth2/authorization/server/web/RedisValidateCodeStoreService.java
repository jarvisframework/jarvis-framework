package com.jarvis.framework.oauth2.authorization.server.web;

import com.jarvis.framework.security.validation.code.ValidateCode;
import com.jarvis.framework.security.validation.code.ValidateCodeStoreService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestAttributes;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

public class RedisValidateCodeStoreService implements ValidateCodeStoreService {
    private final RedisTemplate<Object, Object> redisTemplate;

    public RedisValidateCodeStoreService(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void store(RequestAttributes request, String name, Object value) {
        ValidateCode code = (ValidateCode)value;
        long expireSecond = code.getExpireTime().toEpochSecond(ZoneOffset.ofHours(8));
        long newSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8));
        this.redisTemplate.opsForValue().set(this.getCodeKey(request, name), value, expireSecond - newSecond, TimeUnit.SECONDS);
    }

    public Object get(RequestAttributes request, String name) {
        return this.redisTemplate.opsForValue().get(this.getCodeKey(request, name));
    }

    public void remove(RequestAttributes request, String name) {
        this.redisTemplate.delete(this.getCodeKey(request, name));
    }

    private String getCodeKey(RequestAttributes request, String name) {
        return name;
    }

    public static void main(String[] args) {
        long epochSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8));
    }
}
