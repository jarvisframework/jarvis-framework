package com.jarvis.framework.mybatis.function;

import com.jarvis.framework.function.SerializableFunction;

@FunctionalInterface
public interface ColumnFunction<Entity> extends SerializableFunction<Entity, Object> {
}
