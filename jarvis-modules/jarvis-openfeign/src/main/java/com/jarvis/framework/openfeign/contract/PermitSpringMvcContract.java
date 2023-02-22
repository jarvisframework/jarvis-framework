package com.jarvis.framework.openfeign.contract;

import com.jarvis.framework.constant.CommonConstant;
import com.jarvis.framework.constant.WebMvcConstant;
import com.jarvis.framework.openfeign.annotation.PermitRequest;
import feign.MethodMetadata;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.core.convert.ConversionService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年5月25日
 */
public class PermitSpringMvcContract extends SpringMvcContract {

    private final String permitAccessId;

    public PermitSpringMvcContract(List<AnnotatedParameterProcessor> annotatedParameterProcessors,
                                   ConversionService conversionService, boolean decodeSlash, String permitAccessId) {

        super(annotatedParameterProcessors, conversionService, decodeSlash);

        this.permitAccessId = permitAccessId;

    }

    /**
     *
     * @see org.springframework.cloud.openfeign.support.SpringMvcContract#processAnnotationOnClass(feign.MethodMetadata, java.lang.Class)
     */
    @Override
    protected void processAnnotationOnClass(MethodMetadata data, Class<?> clz) {
        data.template().header(WebMvcConstant.FEIGN_HEADER_NAME, String.valueOf(CommonConstant.YES));
        super.processAnnotationOnClass(data, clz);
    }

    /**
     *
     * @see org.springframework.cloud.openfeign.support.SpringMvcContract#processAnnotationOnMethod(
     *      feign.MethodMetadata,
     *      java.lang.Class)
     */
    @Override
    protected void processAnnotationOnMethod(MethodMetadata data, Annotation methodAnnotation, Method method) {

        if (PermitRequest.class.isInstance(methodAnnotation)
                || methodAnnotation.annotationType().isAnnotationPresent(PermitRequest.class)) {
            data.template().header(WebMvcConstant.PERMIT_HEADER_NAME, permitAccessId);
            return;
        }

        super.processAnnotationOnMethod(data, methodAnnotation, method);

    }

}
