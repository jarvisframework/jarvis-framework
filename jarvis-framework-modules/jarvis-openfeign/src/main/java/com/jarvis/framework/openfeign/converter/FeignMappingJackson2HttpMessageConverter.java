package com.jarvis.framework.openfeign.converter;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jarvis.framework.webmvc.util.WebUtil;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Type;

public class FeignMappingJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {
    private boolean isFeignRequest;

    public FeignMappingJackson2HttpMessageConverter() {
        super(objectMapper(), new MediaType[]{MediaType.APPLICATION_JSON, new MediaType("application", "*+json")});
        this.isFeignRequest = false;
    }

    public FeignMappingJackson2HttpMessageConverter(boolean isFeignRequest) {
        this();
        this.isFeignRequest = isFeignRequest;
    }

    public static ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), DefaultTyping.NON_FINAL, As.PROPERTY);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    protected boolean supports(Class<?> clazz) {
        return !this.isFeign() ? false : super.supports(clazz);
    }

    protected boolean canRead(MediaType mediaType) {
        return !this.isFeign() ? false : super.canRead(mediaType);
    }

    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return !this.isFeign() ? false : super.canRead(clazz, mediaType);
    }

    public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
        return !this.isFeign() ? false : super.canRead(type, contextClass, mediaType);
    }

    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return !this.isFeign() ? false : super.canWrite(clazz, mediaType);
    }

    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        return !this.isFeign() ? false : super.canWrite(type, clazz, mediaType);
    }

    protected boolean canWrite(MediaType mediaType) {
        return !this.isFeign() ? false : super.canWrite(mediaType);
    }

    private boolean isFeign() {
        if (this.isFeignRequest) {
            return true;
        } else {
            RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
            if (null == attributes) {
                return false;
            } else {
                return null != WebUtil.getHeader("X-Feign-Access-Request");
            }
        }
    }
}
