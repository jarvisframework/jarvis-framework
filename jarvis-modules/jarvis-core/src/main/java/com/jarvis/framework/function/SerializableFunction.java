package com.jarvis.framework.function;

import java.io.Serializable;
import java.util.function.Function;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月21日
 */
@FunctionalInterface
public interface SerializableFunction<T, R> extends Function<T, R>, Serializable {

}
