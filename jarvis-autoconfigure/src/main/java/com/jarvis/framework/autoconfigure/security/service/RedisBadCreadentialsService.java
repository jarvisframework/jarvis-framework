package com.jarvis.framework.autoconfigure.security.service;

import com.jarvis.framework.security.authentication.config.BadCreadentialsProperties;
import com.jarvis.framework.security.service.BadCreadentialsService;
import com.jarvis.framework.webmvc.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.authentication.LockedException;

import java.util.List;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月27日
 */
public class RedisBadCreadentialsService implements BadCreadentialsService {

    private static Logger log = LoggerFactory.getLogger(RedisBadCreadentialsService.class);

    private static final String BAD_CREADENTIALS = "bad_creadentials";

    private final RedisTemplate<String, Integer> redisTemplate;

    private final BadCreadentialsProperties badCreadentials;

    // private final int timeout; // 单位为秒

    public RedisBadCreadentialsService(RedisConnectionFactory connectionFactory,
                                       BadCreadentialsProperties badCreadentials) {
        this.redisTemplate = initRedisTemplate(connectionFactory);
        this.badCreadentials = badCreadentials;
        // this.timeout = badCreadentials.getTimeout() * 60;
    }

    private RedisTemplate<String, Integer> initRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        final RedisTemplate<String, Integer> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setValueSerializer(new GenericToStringSerializer<>(Integer.class));
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     *
     * @see com.jarvis.framework.security.service.BadCreadentialsService#checkAccountNonLocked(String)
     */
    @Override
    public void checkAccountNonLocked(String username) {

        if (badCreadentials.isLockUser()) {
            return;
        }

        final BoundValueOperations<String, Integer> boundValueOps = redisTemplate.boundValueOps(getKey(username));
        Integer count = redisTemplate.boundValueOps(getKey(username)).get();

        if (null == count) {
            count = 0;
        }

        if (count >= badCreadentials.getCount()) {
            if (badCreadentials.getTimeout() > 0) {
                final Long expire = (boundValueOps.getExpire() + 59) / 60;
                throw new LockedException(
                        String.format("密码已连续出错[%d]次，用户帐号已被锁定，请于[%s]分钟后再登录！", count, expire));
            } else {
                throw new LockedException(
                        String.format("密码已连续出错[%d]次，用户帐号已被锁定，请联系管理员！", count));
            }
        }
    }

    /**
     *
     * @see com.jarvis.framework.security.service.BadCreadentialsService#increaseErrorCount(String)
     */
    @Override
    public int increaseErrorCount(String username) {

        final byte[] badCreadentialsKey = serializeKey(username);

        final List<?> list = redisTemplate.executePipelined(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.incr(badCreadentialsKey);
                final int timeout = badCreadentials.getTimeout() * 60;
                if (timeout > 0) {
                    connection.expire(badCreadentialsKey, timeout);
                }
                return null;
            }

        });

        final Long count = (Long) list.get(0);

        if (log.isDebugEnabled()) {
            log.debug("用户名[{}]已连续出错[{}]次", username, count);
        }

        return count.intValue();
    }

    private String getKey(String username) {
        final String tenanId = WebUtil.getTenantId();
        final String key = String.format("%s:%s:%s", BAD_CREADENTIALS, tenanId, username);
        return key;
    }

    private byte[] serializeKey(String username) {
        return StringRedisSerializer.UTF_8.serialize(getKey(username));
    }

    /**
     *
     * @see com.jarvis.framework.security.service.BadCreadentialsService#releaseAccountLocked(String)
     */
    @Override
    public void releaseAccountLocked(String username) {
        final byte[] badCreadentialsKey = serializeKey(username);

        redisTemplate.executePipelined(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.expire(badCreadentialsKey, 0);
                return null;
            }

        });
    }

}
