package com.jarvis.framework.mapping;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 雪花算法生成ID（参考网上）
 *
 * 1 0---0000000000 0000000000 0000000000 0000000000 0 --- 000 ---00000 ---00000000000000
 * <p/>
 * 在上面的字符串中，第一位为未使用（实际上也可作为long的符号位），接下来的41位为毫秒级时间，然后3位datacenter标识位，5位机器ID（
 * 并不算标识符，实际是为线程标识），然后14位该毫秒内的当前毫秒内的计数，加起来刚好64位，为一个Long型。
 * <p/>
 * 这样的好处是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞（由datacenter和机器ID作区分），并且效率较高，经测试，
 * snowflake每秒能够产生26万ID左右，完全满足需要。
 *
 *
 * @author qiucs
 * @version 1.0.0 2021年1月14日
 */
public class SnowflakeIdGenerator {

    // 2011-03-14T9:15:00
    private static final long EPOCH_MILLIS = LocalDateTime.of(2011, 3, 14, 9, 15, 0).toInstant(ZoneOffset.of("+8"))
            .toEpochMilli();

    private static final long WORKER_ID_BITS = 5L;

    private static final long DATA_CENTER_ID_BITS = 3L;

    // 最大支持机器节点数0~31，一共32个
    private static final long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);

    // 最大支持数据中心节点数0~7，一共8个
    private static final long MAX_DATA_CENTER_ID = -1L ^ (-1L << DATA_CENTER_ID_BITS);

    // 序列号14位
    private static final long SEQUENCE_BITS = 14L;

    // 机器节点左移5位
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;

    // 数据中心节点左移20位
    private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    // 时间毫秒数左移22位
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;

    private static final long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS);//32767

    private final long workerId;

    private final long dataCenterId;

    private long sequence = 0L;

    private long lastTimeMillis = -1L;

    /**
     * 构造
     *
     * @param workerId 终端ID
     * @param datacenterId 数据中心ID
     */
    public SnowflakeIdGenerator(long workerId, long datacenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        if (datacenterId > MAX_DATA_CENTER_ID || datacenterId < 0) {
            throw new IllegalArgumentException(
                    String.format("datacenter Id can't be greater than %d or less than 0", MAX_DATA_CENTER_ID));
        }
        this.workerId = workerId;
        this.dataCenterId = datacenterId;
    }

    public SnowflakeIdGenerator(long workerId) {
        this(workerId, 0);
    }

    public SnowflakeIdGenerator() {
        this(0, 0);
    }

    /**
     * 下一个ID
     *
     * @return ID
     */
    public synchronized long nextId() {
        long timeMillis = generateTimeMillis();
        if (timeMillis < lastTimeMillis) {
            //如果服务器时间有问题(时钟后退) 报错。
            throw new IllegalStateException(
                    String.format("Clock moved backwards. Refusing to generate id for {}ms", lastTimeMillis - timeMillis));
        }
        if (lastTimeMillis == timeMillis) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                timeMillis = waitUntilNextTimeMillis(lastTimeMillis);
            }
        } else {
            sequence = (sequence + 1) & 1;
        }

        lastTimeMillis = timeMillis;

        return (subtraction(timeMillis) << TIMESTAMP_LEFT_SHIFT) | (dataCenterId << DATA_CENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT) | sequence;
    }

    private long subtraction(long timestamp) {
        long x = timestamp ^ EPOCH_MILLIS;
        long y = x & EPOCH_MILLIS;

        while (y != 0) {
            y = y << 1;
            x = x ^ y;
            y = x & y;
        }

        return x;
    }

    /**
     * 循环等待下一个时间
     *
     * @param lastTimeMillis 上次记录的时间
     * @return 下一个时间
     */
    private long waitUntilNextTimeMillis(long lastTimeMillis) {
        long timeMillis = generateTimeMillis();
        while (timeMillis <= lastTimeMillis) {
            timeMillis = generateTimeMillis();
        }
        return timeMillis;
    }

    /**
     * 生成时间戳
     *
     * @return 时间戳
     */
    private long generateTimeMillis() {
        return System.currentTimeMillis();
    }

}
