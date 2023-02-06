package com.jarvis.framework.mybatis.util;

import com.jarvis.framework.mapping.SnowflakeIdGenerator;
import com.jarvis.framework.mybatis.snowflake.SnowflakeWorkIdHolder;

/**
 * ID生成器工具类
 *
 * @author qiucs
 * @version 1.0.0 2021年1月17日
 */
public final class SnowflakeIdGeneratorUtil {

    private final SnowflakeIdGenerator idGenerator;

    private SnowflakeIdGeneratorUtil() {
        idGenerator = new SnowflakeIdGenerator(SnowflakeWorkIdHolder.getWorkerId());
    }

    public static SnowflakeIdGeneratorUtil getInstance() {
        return Holder.INSTANCE;
    }

    public long nextId() {
        return idGenerator.nextId();
    }

    private static class Holder {

        private static SnowflakeIdGeneratorUtil INSTANCE = new SnowflakeIdGeneratorUtil();
    }
}
