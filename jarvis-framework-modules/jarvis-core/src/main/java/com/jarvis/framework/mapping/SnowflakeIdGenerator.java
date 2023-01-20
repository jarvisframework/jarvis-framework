package com.jarvis.framework.mapping;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class SnowflakeIdGenerator {

    private static final long EPOCH_MILLIS = LocalDateTime.of(2011, 3, 14, 9, 15, 0).toInstant(ZoneOffset.of("+8")).toEpochMilli();
    private static final long WORKER_ID_BITS = 5L;
    private static final long DATA_CENTER_ID_BITS = 3L;
    private static final long MAX_WORKER_ID = 31L;
    private static final long MAX_DATA_CENTER_ID = 7L;
    private static final long SEQUENCE_BITS = 14L;
    private static final long WORKER_ID_SHIFT = 14L;
    private static final long DATA_CENTER_ID_SHIFT = 19L;
    private static final long TIMESTAMP_LEFT_SHIFT = 22L;
    private static final long SEQUENCE_MASK = 16383L;
    private final long workerId;
    private final long dataCenterId;
    private long sequence;
    private long lastTimeMillis;

    public SnowflakeIdGenerator(long workerId, long datacenterId) {
        this.sequence = 0L;
        this.lastTimeMillis = -1L;
        if (workerId <= 31L && workerId >= 0L) {
            if (datacenterId <= 7L && datacenterId >= 0L) {
                this.workerId = workerId;
                this.dataCenterId = datacenterId;
            } else {
                throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", 7L));
            }
        } else {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", 31L));
        }
    }

    public SnowflakeIdGenerator(long workerId) {
        this(workerId, 0L);
    }

    public SnowflakeIdGenerator() {
        this(0L, 0L);
    }

    public synchronized long nextId() {
        long timeMillis = this.generateTimeMillis();
        if (timeMillis < this.lastTimeMillis) {
            throw new IllegalStateException(String.format("Clock moved backwards. Refusing to generate id for {}ms", this.lastTimeMillis - timeMillis));
        } else {
            if (this.lastTimeMillis == timeMillis) {
                this.sequence = this.sequence + 1L & 16383L;
                if (this.sequence == 0L) {
                    timeMillis = this.waitUntilNextTimeMillis(this.lastTimeMillis);
                }
            } else {
                this.sequence = this.sequence + 1L & 1L;
            }

            this.lastTimeMillis = timeMillis;
            return this.subtraction(timeMillis) << 22 | this.dataCenterId << 19 | this.workerId << 14 | this.sequence;
        }
    }

    private long subtraction(long timestamp) {
        long x = timestamp ^ EPOCH_MILLIS;

        for (long y = x & EPOCH_MILLIS; y != 0L; y &= x) {
            y <<= 1;
            x ^= y;
        }

        return x;
    }

    private long waitUntilNextTimeMillis(long lastTimeMillis) {
        long timeMillis;
        for (timeMillis = this.generateTimeMillis(); timeMillis <= lastTimeMillis; timeMillis = this.generateTimeMillis()) {
        }

        return timeMillis;
    }

    private long generateTimeMillis() {
        return System.currentTimeMillis();
    }
}
