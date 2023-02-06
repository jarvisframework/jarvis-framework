package com.jarvis.framework.function;

import java.io.Serializable;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年3月29日
 */
@FunctionalInterface
public interface Getter<Entity extends Serializable> extends SerializableFunction<Entity, Object> {
}
