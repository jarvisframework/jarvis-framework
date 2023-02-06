package com.jarvis.framework.autoconfigure.openfeign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarvis.framework.web.rest.RestResponse;
import com.jarvis.framework.webmvc.web.exception.handler.ExceptionProcessor;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Optional;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年7月8日
 */
public class FeignExceptionProcessor implements ExceptionProcessor {

    private static final Logger log = LoggerFactory.getLogger(FeignExceptionProcessor.class);

    private final ObjectMapper objectMapper;

    public FeignExceptionProcessor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     *
     * @see com.jarvis.framework.webmvc.web.exception.handler.ExceptionProcessor#support(java.lang.Exception)
     */
    @Override
    public boolean support(Exception exception) {
        return FeignException.class.isAssignableFrom(exception.getClass());
    }

    /**
     *
     * @see com.jarvis.framework.webmvc.web.exception.handler.ExceptionProcessor#process(java.lang.Exception)
     */
    @Override
    public RestResponse<?> process(Exception exception) {
        final FeignException fe = (FeignException) exception;
        log.error("openfeign调用出错：{}", exception);
        try {
            final Optional<ByteBuffer> responseBody = fe.responseBody();
            if (responseBody.isPresent()) {
                return objectMapper.readValue(responseBody.get().array(), RestResponse.class);
            }
        } catch (final Exception e) {
            log.error("openfeign异常转换出错：{}", e);
        }
        return RestResponse.error("服务调用出错啦！");
    }

}
