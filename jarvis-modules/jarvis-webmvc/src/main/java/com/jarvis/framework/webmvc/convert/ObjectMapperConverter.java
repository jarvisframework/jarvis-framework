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

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年3月15日
 */
public class ObjectMapperConverter implements ConditionalGenericConverter {

    private final ObjectMapper objectMapper;

    public ObjectMapperConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(Object.class, String.class));
    }

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        final Class<?> sourceClass = sourceType.getObjectType();
        if (String.class == sourceClass) {
            // no conversion required
            return false;
        }
        return true;
    }

    @Override
    @Nullable
    public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (null == source) {
            return null;
        }
        final Class<?> sourceClass = sourceType.getObjectType();
        if (CharSequence.class.isAssignableFrom(sourceClass) ||
                StringWriter.class.isAssignableFrom(sourceClass)) {
            return source.toString();
        }
        try {
            return objectMapper.writeValueAsString(source);
        } catch (final JsonProcessingException e) {
            throw new FrameworkException("对象转json字符串出错", e);
        }
    }

}
