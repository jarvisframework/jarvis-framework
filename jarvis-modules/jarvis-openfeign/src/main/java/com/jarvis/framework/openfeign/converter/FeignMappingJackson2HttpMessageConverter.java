package com.jarvis.framework.openfeign.converter;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jarvis.framework.constant.WebMvcConstant;
import com.jarvis.framework.webmvc.util.WebUtil;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Type;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年10月21日
 */
public class FeignMappingJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {

    private boolean isFeignRequest = false;

    public FeignMappingJackson2HttpMessageConverter() {
        super(objectMapper(), MediaType.APPLICATION_JSON, new MediaType("application", "*+json"));
    }

    public FeignMappingJackson2HttpMessageConverter(boolean isFeignRequest) {
        this();
        this.isFeignRequest = isFeignRequest;
    }

    public static ObjectMapper objectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    /**
     *
     * @see org.springframework.http.converter.AbstractGenericHttpMessageConverter#supports(java.lang.Class)
     */
    @Override
    protected boolean supports(Class<?> clazz) {
        if (!isFeign()) {
            return false;
        }
        return super.supports(clazz);
    }

    /**
     *
     * @see org.springframework.http.converter.AbstractHttpMessageConverter#canRead(org.springframework.http.MediaType)
     */
    @Override
    protected boolean canRead(MediaType mediaType) {
        if (!isFeign()) {
            return false;
        }
        return super.canRead(mediaType);
    }

    /**
     *
     * @see org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter#canRead(java.lang.Class, org.springframework.http.MediaType)
     */
    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        if (!isFeign()) {
            return false;
        }
        return super.canRead(clazz, mediaType);
    }

    /**
     *
     * @see org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter#canRead(java.lang.reflect.Type, java.lang.Class,
     *      org.springframework.http.MediaType)
     */
    @Override
    public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
        if (!isFeign()) {
            return false;
        }
        return super.canRead(type, contextClass, mediaType);
    }

    /**
     *
     * @see org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter#canWrite(java.lang.Class, org.springframework.http.MediaType)
     */
    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        if (!isFeign()) {
            return false;
        }
        return super.canWrite(clazz, mediaType);
    }

    /**
     *
     * @see org.springframework.http.converter.AbstractGenericHttpMessageConverter#canWrite(java.lang.reflect.Type, java.lang.Class,
     *      org.springframework.http.MediaType)
     */
    @Override
    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        if (!isFeign()) {
            return false;
        }
        return super.canWrite(type, clazz, mediaType);
    }

    /**
     *
     * @see org.springframework.http.converter.AbstractHttpMessageConverter#canWrite(org.springframework.http.MediaType)
     */
    @Override
    protected boolean canWrite(MediaType mediaType) {
        if (!isFeign()) {
            return false;
        }
        return super.canWrite(mediaType);
    }

    private boolean isFeign() {
        if (isFeignRequest) {
            return true;
        }
        final RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (null == attributes) {
            return false;
        }
        return null != WebUtil.getHeader(WebMvcConstant.FEIGN_HEADER_NAME);
    }
}
