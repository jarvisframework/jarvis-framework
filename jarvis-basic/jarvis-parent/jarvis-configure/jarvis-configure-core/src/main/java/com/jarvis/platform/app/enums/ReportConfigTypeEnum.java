package com.jarvis.platform.app.enums;

import com.jarvis.platform.app.enums.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 报表配置类型枚举
 * @author wangdh
 * @date 2022-09-06 19:49
 */
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public enum ReportConfigTypeEnum implements BaseEnum<String> {

    /**
     * 剪切
     */
    GROUP("group", "分组"),
    /**
     * 复制
     */
    SORT("sort", "排序");

    private final String value;

    private final String text;
}
