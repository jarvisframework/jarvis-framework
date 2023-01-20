package com.jarvis.framework.openfeign.contract;

import com.jarvis.framework.openfeign.annotation.PermitRequest;
import feign.MethodMetadata;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.core.convert.ConversionService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public class PermitSpringMvcContract extends SpringMvcContract {
    private final String permitAccessId;

    public PermitSpringMvcContract(List<AnnotatedParameterProcessor> annotatedParameterProcessors, ConversionService conversionService, boolean decodeSlash, String permitAccessId) {
        super(annotatedParameterProcessors, conversionService, decodeSlash);
        this.permitAccessId = permitAccessId;
    }

    protected void processAnnotationOnClass(MethodMetadata data, Class<?> clz) {
        data.template().header("X-Feign-Access-Request", new String[]{String.valueOf(1)});
        super.processAnnotationOnClass(data, clz);
    }

    protected void processAnnotationOnMethod(MethodMetadata data, Annotation methodAnnotation, Method method) {
        if (!PermitRequest.class.isInstance(methodAnnotation) && !methodAnnotation.annotationType().isAnnotationPresent(PermitRequest.class)) {
            super.processAnnotationOnMethod(data, methodAnnotation, method);
        } else {
            data.template().header("X-Request-Access-Id", new String[]{this.permitAccessId});
        }
    }
}
