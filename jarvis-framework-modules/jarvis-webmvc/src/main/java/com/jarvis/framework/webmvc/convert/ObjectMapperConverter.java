package com.jarvis.framework.webmvc.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarvis.framework.core.exception.FrameworkException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.lang.Nullable;

import java.io.StringWriter;
import java.util.Collections;
import java.util.Set;

public class ObjectMapperConverter implements ConditionalGenericConverter {
    private final ObjectMapper objectMapper;

    public ObjectMapperConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(Object.class, String.class));
    }

    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        Class<?> sourceClass = sourceType.getObjectType();
        return String.class != sourceClass;
    }

    @Nullable
    public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (null == source) {
            return null;
        } else {
            Class<?> sourceClass = sourceType.getObjectType();
            if (!CharSequence.class.isAssignableFrom(sourceClass) && !StringWriter.class.isAssignableFrom(sourceClass)) {
                try {
                    return this.objectMapper.writeValueAsString(source);
                } catch (JsonProcessingException var6) {
                    throw new FrameworkException("对象转json字符串出错", var6);
                }
            } else {
                return source.toString();
            }
        }
    }
}
