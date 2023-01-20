package com.jarvis.framework.mybatis.util;

public final class UuidGeneratorUtil {

    private final UuidGenerator uuidGenerator;

    private UuidGeneratorUtil() {
        this.uuidGenerator = new UuidGenerator();
    }

    public static UuidGeneratorUtil getInstance() {
        return UuidGeneratorUtil.Holder.INSTANCE;
    }

    public String nextId() {
        return this.uuidGenerator.generate();
    }

    private static class Holder {

        private static UuidGeneratorUtil INSTANCE = new UuidGeneratorUtil();

    }
}
