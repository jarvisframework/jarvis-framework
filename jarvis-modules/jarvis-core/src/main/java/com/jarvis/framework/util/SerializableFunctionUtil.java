package com.jarvis.framework.util;

import com.jarvis.framework.function.SerializableFunction;

import java.beans.Introspector;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月21日
 */
public class SerializableFunctionUtil {

    /**
     * 获取Getter对应的属性
     *
     * @param function
     * @return
     */
    public static String getFieldName(SerializableFunction<?, ?> function) {
        try {
            // 第1步 获取SerializedLambda
            final Method method = function.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            final SerializedLambda serializedLambda = (SerializedLambda) method.invoke(function);
            // 第2步 implMethodName 即为Field对应的Getter方法名
            final String implMethodName = serializedLambda.getImplMethodName();
            if (implMethodName.startsWith("get") && implMethodName.length() > 3) {
                return Introspector.decapitalize(implMethodName.substring(3));
            } else if (implMethodName.startsWith("is") && implMethodName.length() > 2) {
                return Introspector.decapitalize(implMethodName.substring(2));
            } else if (implMethodName.startsWith("lambda$")) {
                throw new IllegalArgumentException("SerializableFunction不能传递lambda表达式,只能使用方法引用");
            } else {
                throw new IllegalArgumentException(implMethodName + "不是Getter方法引用");
            }
        } catch (final Exception e) {
            throw new IllegalArgumentException("获取get方法出错", e);
        }
    }
}
