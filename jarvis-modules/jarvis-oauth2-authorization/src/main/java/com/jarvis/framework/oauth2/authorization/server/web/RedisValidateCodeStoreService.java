package com.jarvis.framework.oauth2.authorization.server.web;

import com.jarvis.framework.security.validation.code.ValidateCode;
import com.jarvis.framework.security.validation.code.ValidateCodeStoreService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestAttributes;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月26日
 */
public class RedisValidateCodeStoreService implements ValidateCodeStoreService {

    private final RedisTemplate<Object, Object> redisTemplate;

    public RedisValidateCodeStoreService(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     *
     * @see com.jarvis.framework.security.validation.code.ValidateCodeStoreService#store(org.springframework.web.context.request.RequestAttributes,
     *      java.lang.String, java.lang.Object)
     */
    @Override
    public void store(RequestAttributes request, String name, Object value) {
        final ValidateCode code = (ValidateCode) value;
        final long expireSecond = code.getExpireTime().toEpochSecond(ZoneOffset.ofHours(8));
        final long newSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8));
        redisTemplate.opsForValue().set(getCodeKey(request, name), value, (expireSecond - newSecond), TimeUnit.SECONDS);
    }

    /**
     *
     * @see com.jarvis.framework.security.validation.code.ValidateCodeStoreService#get(org.springframework.web.context.request.RequestAttributes,
     *      java.lang.String)
     */
    @Override
    public Object get(RequestAttributes request, String name) {
        return redisTemplate.opsForValue().get(getCodeKey(request, name));
    }

    /**
     *
     * @see com.jarvis.framework.security.validation.code.ValidateCodeStoreService#remove(org.springframework.web.context.request.RequestAttributes,
     *      java.lang.String)
     */
    @Override
    public void remove(RequestAttributes request, String name) {
        redisTemplate.delete(getCodeKey(request, name));
    }

    private String getCodeKey(RequestAttributes request, String name) {
        return name;
    }

    public static void main(String[] args) {
        final long epochSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8));

    }

}

