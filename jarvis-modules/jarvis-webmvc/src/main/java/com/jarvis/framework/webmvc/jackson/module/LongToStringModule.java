package com.jarvis.framework.webmvc.jackson.module;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.jarvis.framework.webmvc.jackson.serializer.LongToStringSerializer;

/**
 * java 中的long类型，在js中会丢失精度，因此在返回给前端时，要转成string类型
 *
 * @author qiucs
 * @version 1.0.0 2019年9月12日
 */
public class LongToStringModule extends SimpleModule {

    /**
     *
     */
    private static final long serialVersionUID = 591800117057871808L;

    public LongToStringModule() {
        super(PackageVersion.VERSION);
        this.addSerializer(Long.class, LongToStringSerializer.INSTANCE);
        this.addSerializer(Long.TYPE, LongToStringSerializer.INSTANCE);
    }
}
