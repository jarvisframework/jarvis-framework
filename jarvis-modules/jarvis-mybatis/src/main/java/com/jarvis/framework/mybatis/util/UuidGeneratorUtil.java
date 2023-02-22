package com.jarvis.framework.mybatis.util;

import com.jarvis.framework.mapping.UuidGenerator;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月28日
 */
public final class UuidGeneratorUtil {

    private final UuidGenerator uuidGenerator;

    private UuidGeneratorUtil() {
        uuidGenerator = new UuidGenerator();
    }

    public static UuidGeneratorUtil getInstance() {
        return Holder.INSTANCE;
    }

    public String nextId() {
        return uuidGenerator.generate();
    }

    private static class Holder {

        private static UuidGeneratorUtil INSTANCE = new UuidGeneratorUtil();
    }
}
