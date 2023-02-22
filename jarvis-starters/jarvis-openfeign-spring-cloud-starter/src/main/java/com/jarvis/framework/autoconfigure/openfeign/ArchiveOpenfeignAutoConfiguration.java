package com.jarvis.framework.autoconfigure.openfeign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarvis.framework.openfeign.contract.PermitSpringMvcContract;
import com.jarvis.framework.openfeign.converter.FeignConverterObjectFactory;
import com.jarvis.framework.openfeign.converter.FeignMappingJackson2HttpMessageConverter;
import com.jarvis.framework.openfeign.intereptor.AuthorizationHeaderRequestInterceptor;
import com.jarvis.framework.webmvc.web.exception.handler.ExceptionProcessor;
import feign.Contract;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.form.MultipartFormContentProcessor;
import feign.form.spring.SpringFormEncoder;
import feign.optionals.OptionalDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.cloud.openfeign.support.AbstractFormWriter;
import org.springframework.cloud.openfeign.support.FeignEncoderProperties;
import org.springframework.cloud.openfeign.support.PageableSpringEncoder;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionService;

import java.util.ArrayList;
import java.util.List;

import static feign.form.ContentType.MULTIPART;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年6月4日
 */
@EnableFeignClients(basePackages = { "com.jarvis.platform.client" })
@Configuration(proxyBeanMethods = false)
public class ArchiveOpenfeignAutoConfiguration {

    @Autowired(required = false)
    private final List<AnnotatedParameterProcessor> parameterProcessors = new ArrayList<>();

    @Autowired(required = false)
    private FeignClientProperties feignClientProperties;

    @Value("${spring.security.permit-access-id}")
    private String permitAccessId;

    @Autowired(required = false)
    private SpringDataWebProperties springDataWebProperties;

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Autowired(required = false)
    private FeignEncoderProperties encoderProperties;

    private FeignConverterObjectFactory feignConverterObjectFactory;

    @Bean
    public Decoder feignDecoder() {
        return new OptionalDecoder(new ResponseEntityDecoder(new SpringDecoder(feignConverterObjectFactory())));
    }

    @Bean
    @ConditionalOnMissingClass("org.springframework.data.domain.Pageable")
    public Encoder feignEncoder(ObjectProvider<AbstractFormWriter> formWriterProvider) {
        return springEncoder(formWriterProvider, encoderProperties);
    }

    @Bean
    @ConditionalOnClass(name = "org.springframework.data.domain.Pageable")
    public Encoder feignEncoderPageable(ObjectProvider<AbstractFormWriter> formWriterProvider) {
        final PageableSpringEncoder encoder = new PageableSpringEncoder(
                springEncoder(formWriterProvider, encoderProperties));

        if (springDataWebProperties != null) {
            encoder.setPageParameter(springDataWebProperties.getPageable().getPageParameter());
            encoder.setSizeParameter(springDataWebProperties.getPageable().getSizeParameter());
            encoder.setSortParameter(springDataWebProperties.getSort().getSortParameter());
        }
        return encoder;
    }

    private FeignConverterObjectFactory feignConverterObjectFactory() {
        if (null == feignConverterObjectFactory) {
            feignConverterObjectFactory = new FeignConverterObjectFactory(messageConverters);
        }
        return feignConverterObjectFactory;
    }

    private Encoder springEncoder(ObjectProvider<AbstractFormWriter> formWriterProvider,
                                  FeignEncoderProperties encoderProperties) {
        final AbstractFormWriter formWriter = formWriterProvider.getIfAvailable();

        if (formWriter != null) {
            return new SpringEncoder(new SpringPojoFormEncoder(formWriter), feignConverterObjectFactory(),
                    encoderProperties);
        } else {
            return new SpringEncoder(new SpringFormEncoder(), feignConverterObjectFactory(), encoderProperties);
        }
    }

    private class SpringPojoFormEncoder extends SpringFormEncoder {

        SpringPojoFormEncoder(AbstractFormWriter formWriter) {
            super();

            final MultipartFormContentProcessor processor = (MultipartFormContentProcessor) getContentProcessor(
                    MULTIPART);
            processor.addFirstWriter(formWriter);
        }

    }

    @Bean
    public Contract feignContract(@Qualifier("mvcConversionService") ConversionService mvcConversionService) {
        final boolean decodeSlash = feignClientProperties == null || feignClientProperties.isDecodeSlash();
        return new PermitSpringMvcContract(this.parameterProcessors, mvcConversionService, decodeSlash,
                permitAccessId);
    }

    @Bean
    public RequestInterceptor authorizationHeaderRequestInterceptor() {
        return new AuthorizationHeaderRequestInterceptor();
    }

    @Bean
    ExceptionProcessor feignExceptionProcessor(ObjectMapper objectMapper) {
        return new FeignExceptionProcessor(objectMapper);
    }

    @Bean
    @Order(Integer.MIN_VALUE + 100)
    FeignMappingJackson2HttpMessageConverter feignMappingJackson2HttpMessageConverter() {
        return new FeignMappingJackson2HttpMessageConverter();
    }
}
