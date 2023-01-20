package com.jarvis.platform.app.enums;

import com.jarvis.framework.core.code.CodeEnum;
import lombok.AllArgsConstructor;

/**
 * @Author xukaiqian
 * @Version 1.0.0 2022年10月18日
 */
@AllArgsConstructor
public enum MetadataTypeEnum implements CodeEnum<String> {

    SIMPLE("simple", "简单型"),
    COMPLEX("complex", "复合型"),
    CONTAINER("container", "容器型");

    private final String value;
    private final String text;

    @Override
    public String value() {
        return value;
    }

    @Override
    public String text() {
        return text;
    }
}
