package com.jarvis.framework.util;

import com.jarvis.framework.core.code.CodeEnum;
import com.jarvis.framework.core.select.SelectOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年9月29日
 */
public abstract class CodeEnumUtil {

    /**
     * 获取枚举集合(value -> text)
     *
     * @param <V> 编码值类型
     * @param <E> 枚举类型
     * @param clazz 枚举类
     * @return Map
     */
    public static <V, E extends CodeEnum<V>> Map<V, String> codeEnumMap(Class<E> clazz) {
        final Map<V, String> codeValueMap = new HashMap<>();
        final E[] constants = clazz.getEnumConstants();
        Stream.of(constants).forEach(e -> {
            codeValueMap.put(e.value(), e.text());
        });

        return codeValueMap;
    }

    /**
     * 获取枚举集合(text -> value)
     *
     * @param <V> 编码值类型
     * @param <E> 枚举类型
     * @param clazz 枚举类
     * @return Map
     */
    public static <V, E extends CodeEnum<V>> Map<String, V> textEnumMap(Class<E> clazz) {
        final Map<String, V> textValueMap = new HashMap<>();
        final E[] constants = clazz.getEnumConstants();
        Stream.of(constants).forEach(e -> {
            textValueMap.put(e.text(), e.value());
        });

        return textValueMap;
    }

    /**
     * 获取枚举集合
     *
     * @param <V> 编码值类型
     * @param <E> 枚举类型
     * @param clazz 枚举类
     * @return List
     */
    public static <V, E extends CodeEnum<V>> List<SelectOption> selectOptions(Class<E> clazz) {
        final List<SelectOption> selectOptions = new ArrayList<>();
        final E[] constants = clazz.getEnumConstants();
        Stream.of(constants).forEach(e -> {
            final SelectOption option = new SelectOption();
            option.value(e.value()).text(e.text());
            selectOptions.add(option);
        });

        return selectOptions;
    }
}
