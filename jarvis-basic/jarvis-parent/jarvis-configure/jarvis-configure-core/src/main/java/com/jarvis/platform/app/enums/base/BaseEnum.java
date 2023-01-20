package com.jarvis.platform.app.enums.base;

import com.jarvis.framework.core.code.CodeEnum;
import com.jarvis.framework.core.exception.BusinessException;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author wangdh
 * @date 2022-09-06 19:50
 */
public interface BaseEnum<T> extends CodeEnum<T> {

    /**
     * 根据枚举值获取枚举信息
     *
     * @param enumClass 枚举class
     * @param value     枚举值
     * @return 枚举信息
     */
    static <E extends Enum> E get(Class<E> enumClass, Object value) {
        if (value == null) {
            throw new IllegalArgumentException("枚举值不能为空");
        }
        if (enumClass.isAssignableFrom(BaseEnum.class)) {
            throw new IllegalArgumentException("找不到实现关系");
        }
        E[] enums = enumClass.getEnumConstants();
        return Arrays.stream(enums)
            .filter(typeEnum -> Objects.equals(((BaseEnum) typeEnum).value(), value))
            .findFirst()
            .orElseThrow(() -> new BusinessException("值：" + value + "在枚举：" + enumClass.getName() + "未定义"));
    }
}
