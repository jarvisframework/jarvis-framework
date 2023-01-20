package comjarvis.framework.redis.util;

public class RedisKeyUtil {

    public static String cacheKey(String cacheName, String key) {
        return cacheName + ":" + key;
    }

}
