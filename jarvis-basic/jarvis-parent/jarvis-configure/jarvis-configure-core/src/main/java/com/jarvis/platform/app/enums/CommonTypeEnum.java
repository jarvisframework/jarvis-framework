package com.jarvis.platform.app.enums;

import com.jarvis.platform.app.enums.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 配置类型枚举
 * @author wangdh
 * @date 2022-10-27 09:19
 */
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public enum CommonTypeEnum implements BaseEnum<String> {

    NUMBER("number", "编号列表/排序");

    private final String value;

    private final String text;
}
