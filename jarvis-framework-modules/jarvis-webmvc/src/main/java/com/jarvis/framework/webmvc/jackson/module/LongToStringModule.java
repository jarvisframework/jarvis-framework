package com.jarvis.framework.webmvc.jackson.module;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.jarvis.framework.webmvc.jackson.serializer.LongToStringSerializer;

public class LongToStringModule extends SimpleModule {
    private static final long serialVersionUID = 591800117057871808L;

    public LongToStringModule() {
        super(PackageVersion.VERSION);
        this.addSerializer(Long.class, LongToStringSerializer.INSTANCE);
        this.addSerializer(Long.TYPE, LongToStringSerializer.INSTANCE);
    }
}
