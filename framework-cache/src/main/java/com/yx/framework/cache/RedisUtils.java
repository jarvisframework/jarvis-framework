package com.yx.framework.cache;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yx.framework.tool.util.SerializeUtils;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>Redis工具类</p>
 *
 * @author 王涛
 * @date 2019-11-07 19:32:35
 */
public class RedisUtils {

    private static RedisTemplate<String, Object> redisTemplate;

    /**
     * 默认过期时长，单位：秒
     */
    public static final Long DEFAULT_EXPIRE = 60 * 60 * 4L;

    /**
     * 不设置过期时长
     */
    public static final long NOT_EXPIRE = -1;

    /**
     * 一周的秒数
     */

    private static final long WEEK_SECONDS = 7 * 24 * 60 * 60;

    // =============================common============================

    /**
     * 模糊匹配keys
     *
     * @param keys
     * @return
     */
    public static Set<String> keys(String keys) {
        return redisTemplate.keys(keys);
    }

    /**
     * 重名名key，如果newKey已经存在，则newKey的原值被覆盖
     *
     * @param oldKey
     * @param newKey
     */
    public static void renameKey(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * newKey不存在时才重命名
     *
     * @param oldKey
     * @param newKey
     * @return 修改成功返回true
     */
    public static boolean renameKeyNotExist(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 删除缓存
     *
     * @param keys 可以传一个或多个
     */
    @SuppressWarnings("unchecked")
    public static void deleteKey(String... keys) {

        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                redisTemplate.delete(keys[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(keys));
            }
        }
    }

    /**
     * 根据集合删除缓存
     *
     * @param keys
     */
    public static void deleteKey(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public static boolean expireKey(String key, long time) {
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 指定key在指定的日期过期
     *
     * @param key  键
     * @param date 日期
     */
    public static boolean expireKeyAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 将key设置为永久有效
     *
     * @param key 键
     */
    public static boolean persistKey(String key) {
        return redisTemplate.persist(key);
    }

    /**
     * 获取缓存过期时间
     *
     * @param key 键
     * @return 时间(秒) 返回0代表为永久有效
     */
    public static long getKeyExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }


    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public static boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    // ============================String=============================

    /**
     * 获取java对象
     *
     * @param key   键
     * @param clazz clazz
     * @param <T>   泛型
     * @return
     */
    public static <T> T get(String key, Class<T> clazz) {
        Object object = get(key);
        if (object == null) {
            return null;
        }
        return ((JSONObject) object).toJavaObject(clazz);
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public static boolean set(String key, Object value) {
        //先查询是否存在,存在删除
        if (hasKey(key)) {
            if (!redisTemplate.delete(key)) {
                return false;
            }
        }
        //在新增
        redisTemplate.opsForValue().set(key, value);
        return true;
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public static boolean set(String key, Object value, long time) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            set(key, value);
        }
        return true;
    }


    /**
     * (根据key验证重复)如果为空，key和value设置成功，返回成功，如果存在，返回失败
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean setIfAbsent(String key, Object value) {
        try {
            return redisTemplate.opsForValue().setIfAbsent(key, value);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * (根据key验证重复)如果为空，key和value设置成功，返回成功，如果存在，返回失败
     *
     * @param key
     * @param value
     * * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return
     */
    public static boolean setIfAbsent(String key, Object value,long time) {
        try {
            return redisTemplate.opsForValue().setIfAbsent(key, value,time,TimeUnit.SECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public static long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public static long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    // ===============================Map=================================

    /**
     * HashGet
     *
     * @param key  键
     * @param item 项
     * @return 值
     */
    public static Object hashGet(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public static Map<Object, Object> hmGet(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public static boolean hmSet(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
        return true;
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public static boolean hmSet(String key, Map<String, Object> map, long time) {
        redisTemplate.opsForHash().putAll(key, map);
        if (time > 0) {
            expireKey(key, time);
        }
        return true;
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public static boolean hSet(String key, String item, Object value) {
        redisTemplate.opsForHash().put(key, item, value);
        return true;
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public static boolean hSet(String key, String item, Object value, long time) {
        redisTemplate.opsForHash().put(key, item, value);
        if (time > 0) {
            expireKey(key, time);
        }
        return true;
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public static void hDel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public static boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public static double hIncr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public static double hDecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    // ============================Set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public static Set<Object> sGet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public static boolean sHasKey(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSet(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static Long sSetAndTime(String key, long time, Object... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        if (time > 0) {
            expireKey(key, time);
        }
        return count;
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public static long sGetSetSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public static long setRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    // ===============================List=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param clazz 开始
     * @param <T>
     * @return
     */
    public static <T> List<T> lGet(String key, Class<T> clazz) {
        return lGet(key, 0, -1, clazz);
    }

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @param clazz 类型
     * @param <T>
     * @return
     */
    public static <T> List<T> lGet(String key, long start, long end, Class<T> clazz) {
        List<Object> objects = lGet(key, start, end);
        List<T> result = new ArrayList<>();
        if(objects.size()>0){
            for (Object o : (JSONArray) objects.get(0)) {
                result.add(((JSONObject) o).toJavaObject(clazz));
            }
        }
        return result;
    }

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return
     */
    public static List<Object> lGet(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public static long lGetListSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public static Object lGetIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 将list放入缓存
     *
     * @param key    键
     * @param values 值
     * @return
     */
    public static boolean lSet(String key, Object... values) {

        //先查询是否存在,存在删除
        if (hasKey(key)) {
            if (!redisTemplate.delete(key)) {
                return false;
            }
        }
        redisTemplate.opsForList().rightPushAll(key, values);
        return true;
    }

    /**
     * 将list放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值
     * @return
     */
    public static boolean lSet(String key, long time, Object... values) {
        redisTemplate.opsForList().rightPushAll(key, values);
        if (time > 0) {
            expireKey(key, time);
        }
        return true;
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public static boolean lUpdateIndex(String key, long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
        return true;
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public static long lRemove(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    // ===============================Other=================================

    /**
     * key-value取值
     * 获取对象
     *
     * @param key
     * @return
     */
    public static <T> T getObject(final String key) {
        return redisTemplate.execute((RedisCallback<T>) connection -> {
            byte[] keyBytes = redisTemplate.getStringSerializer().serialize(key);
            if (connection.exists(keyBytes)) {
                byte[] valueBytes = connection.get(keyBytes);

                long exp = connection.ttl(keyBytes);
                if (exp > 0) {
                    @SuppressWarnings("unchecked")
                    T value = (T) SerializeUtils.unSerialize(valueBytes);
                    return value;
                }
            }
            return null;
        });
    }

    /**
     * 保存对象数据
     *
     * @param key   key
     * @param value 对象数据
     * @param time  有效期
     */
    public static void putObject(final String key, Object value, final Long time) {
        final byte[] valueBytes = SerializeUtils.serialize(value);
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] keyBytes = redisTemplate.getStringSerializer().serialize(key);
            if (connection.exists(keyBytes)) {
                connection.del(keyBytes);
            }
            connection.set(keyBytes, valueBytes);
            if (null != time && time > 0) {
                connection.expire(keyBytes, time);
            }
            return null;
        });
    }

    /**
     * RedisTemplate实例对象赋值
     *
     * @param redisTemplate 实例对象
     */
    public static void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        RedisUtils.redisTemplate = redisTemplate;
    }
}
