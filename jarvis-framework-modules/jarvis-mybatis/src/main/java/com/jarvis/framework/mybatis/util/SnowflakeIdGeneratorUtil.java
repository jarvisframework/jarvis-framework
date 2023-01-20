package com.jarvis.framework.mybatis.util;

import com.jarvis.framework.mybatis.snowflake.SnowflakeWorkIdHolder;

public final class SnowflakeIdGeneratorUtil {
    private final SnowflakeIdGenerator idGenerator;

    private SnowflakeIdGeneratorUtil() {
        this.idGenerator = new SnowflakeIdGenerator(SnowflakeWorkIdHolder.getWorkerId());
    }

    public static SnowflakeIdGeneratorUtil getInstance() {
        return SnowflakeIdGeneratorUtil.Holder.INSTANCE;
    }

    public long nextId() {
        return this.idGenerator.nextId();
    }

    private static class Holder {

        private static SnowflakeIdGeneratorUtil INSTANCE = new SnowflakeIdGeneratorUtil();

    }
}
