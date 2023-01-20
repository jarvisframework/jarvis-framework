package com.jarvis.framework.autoconfigure.openfeign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarvis.framework.web.rest.RestResponse;
import com.jarvis.framework.webmvc.web.exception.handler.ExceptionProcessor;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Optional;

public class FeignExceptionProcessor implements ExceptionProcessor {
    private static final Logger log = LoggerFactory.getLogger(FeignExceptionProcessor.class);
    private final ObjectMapper objectMapper;

    public FeignExceptionProcessor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public boolean support(Exception exception) {
        return FeignException.class.isAssignableFrom(exception.getClass());
    }

    public RestResponse<?> process(Exception exception) {
        FeignException fe = (FeignException)exception;
        log.error("openfeign调用出错：{}", exception);

        try {
            Optional<ByteBuffer> responseBody = fe.responseBody();
            if (responseBody.isPresent()) {
                return (RestResponse)this.objectMapper.readValue(((ByteBuffer)responseBody.get()).array(), RestResponse.class);
            }
        } catch (Exception var4) {
            log.error("openfeign异常转换出错：{}", var4);
        }

        return RestResponse.error("服务调用出错啦！");
    }
}
