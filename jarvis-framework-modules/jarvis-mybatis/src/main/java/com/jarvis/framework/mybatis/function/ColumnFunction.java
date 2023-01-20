package com.jarvis.framework.mybatis.function;

@FunctionalInterface
public interface ColumnFunction<Entity> extends SerializableFunction<Entity, Object> {
}
