package com.jarvis.framework.util;

import com.jarvis.framework.function.SerializableFunction;

import java.beans.Introspector;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

public class SerializableFunctionUtil {

    public static String getFieldName(SerializableFunction<?, ?> function) {
        try {
            Method method = function.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda)method.invoke(function);
            String implMethodName = serializedLambda.getImplMethodName();
            if (implMethodName.startsWith("get") && implMethodName.length() > 3) {
                return Introspector.decapitalize(implMethodName.substring(3));
            } else if (implMethodName.startsWith("is") && implMethodName.length() > 2) {
                return Introspector.decapitalize(implMethodName.substring(2));
            } else if (implMethodName.startsWith("lambda$")) {
                throw new IllegalArgumentException("SerializableFunction不能传递lambda表达式,只能使用方法引用");
            } else {
                throw new IllegalArgumentException(implMethodName + "不是Getter方法引用");
            }
        } catch (Exception var4) {
            throw new IllegalArgumentException("获取get方法出错", var4);
        }
    }
}
