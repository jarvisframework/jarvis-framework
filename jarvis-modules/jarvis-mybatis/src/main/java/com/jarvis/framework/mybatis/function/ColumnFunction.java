package com.jarvis.framework.mybatis.function;

import com.jarvis.framework.function.SerializableFunction;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月22日
 */
@FunctionalInterface
public interface ColumnFunction<Entity> extends SerializableFunction<Entity, Object> {

}
