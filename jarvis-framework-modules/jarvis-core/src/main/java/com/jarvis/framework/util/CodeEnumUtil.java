package com.jarvis.framework.util;

import com.jarvis.framework.core.code.CodeEnum;
import com.jarvis.framework.core.select.SelectOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public abstract class CodeEnumUtil {

    public static <V, E extends CodeEnum<V>> Map<V, String> codeEnumMap(Class<E> clazz) {
        Map<V, String> codeValueMap = new HashMap();
        E[] constants = clazz.getEnumConstants();
        Stream.of(constants).forEach((e) -> {
            codeValueMap.put(e.value(), e.text());
        });
        return codeValueMap;
    }

    public static <V, E extends CodeEnum<V>> Map<String, V> textEnumMap(Class<E> clazz) {
        Map<String, V> textValueMap = new HashMap();
        E[] constants = clazz.getEnumConstants();
        Stream.of(constants).forEach((e) -> {
            textValueMap.put(e.text(), e.value());
        });
        return textValueMap;
    }

    public static <V, E extends CodeEnum<V>> List<SelectOption> selectOptions(Class<E> clazz) {
        List<SelectOption> selectOptions = new ArrayList();
        E[] constants = clazz.getEnumConstants();
        Stream.of(constants).forEach((e) -> {
            SelectOption option = new SelectOption();
            option.value(e.value()).text(e.text());
            selectOptions.add(option);
        });
        return selectOptions;
    }
}
