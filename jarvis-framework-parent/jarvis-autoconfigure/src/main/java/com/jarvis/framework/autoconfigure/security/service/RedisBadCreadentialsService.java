package com.jarvis.framework.autoconfigure.security.service;

import com.jarvis.framework.autoconfigure.mybatis.DruidExtendProperties;
import com.jarvis.framework.security.authentication.config.BadCreadentialsProperties;
import com.jarvis.framework.security.service.BadCreadentialsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.LockedException;

public class RedisBadCreadentialsService implements BadCreadentialsService {
    private static final String BAD_CREADENTIALS = "bad_creadentials";
    private final RedisTemplate<String, Integer> redisTemplate;
    private final BadCreadentialsProperties badCreadentials;
    private static Logger log = LoggerFactory.getLogger(RedisBadCreadentialsService.class);

    public RedisBadCreadentialsService(RedisConnectionFactory a, BadCreadentialsProperties a) {
        a.redisTemplate = a.initRedisTemplate(a);
        a.badCreadentials = a;
    }

    public int increaseErrorCount(String a) {
        final byte[] var2 = a.serializeKey(a);
        Long var3 = (Long)a.redisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection axx) throws DataAccessException {
                axx.incr(var2);
                int var2x = a.badCreadentials.getTimeout() * 60;
                if (var2x > 0) {
                    axx.expire(var2, (long)var2x);
                }

                return null;
            }
        }).get(0);
        if (log.isDebugEnabled()) {
            log.debug(DruidExtendProperties.oOoOOo("甙扣吼\u000fJ)l嶦迯纹凋镍j/L\t欐"), a, var3);
        }

        return var3.intValue();
    }

    public void releaseAccountLocked(String a) {
        final String a = a.serializeKey(a);
        a.redisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection axx) throws DataAccessException {
                axx.expire(a, 0L);
                return null;
            }
        });
    }

    public void checkAccountNonLocked(String a) {
        BoundValueOperations var2 = a.redisTemplate.boundValueOps(a.getKey(a));
        String a = (Integer)a.redisTemplate.boundValueOps(a.getKey(a)).get();
        if (null == a) {
            a = 0;
        }

        if (a >= a.badCreadentials.getCount()) {
            String var10002;
            Object[] var10003;
            boolean var10005;
            if (a.badCreadentials.getTimeout() > 0) {
                Long var4 = (var2.getExpire() + 59L) / 60L;
                var10002 = DruidExtendProperties.oOoOOo("宒砰嶦迯纹凋镍jqU\t欐ｘ甙扣帡厣巃裿锰寎］讣亿\u000f\u0014'l剒钮呚冼瘯彤");
                var10003 = new Object[2];
                var10005 = true;
                var10003[0] = a;
                var10003[1] = var4;
                throw new LockedException(String.format(var10002, var10003));
            } else {
                var10002 = DruidExtendProperties.oOoOOo("宒砰嶦迯纹凋镍jqU\t欐ｘ甙扣帡厣巃裿锰寎］讣亿讣聥粯箐瑒呩");
                var10003 = new Object[1];
                var10005 = true;
                var10003[0] = a;
                throw new LockedException(String.format(var10002, var10003));
            }
        }
    }
}
