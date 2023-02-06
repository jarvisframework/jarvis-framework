package com.jarvis.framework.autoconfigure.webmvc.exception;

import com.jarvis.framework.core.exception.BusinessException;
import com.jarvis.framework.core.exception.FrameworkException;
import com.jarvis.framework.util.FileUtil;
import com.jarvis.framework.web.exception.HttpStatusException;
import com.jarvis.framework.web.rest.RestResponse;
import com.jarvis.framework.webmvc.web.exception.handler.ExceptionProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年7月8日
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired(required = false)
    private List<ExceptionProcessor> exceptionProcessors;

    @ExceptionHandler({ Exception.class, RuntimeException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResponse<?> globalException(Exception e) {
        if (log.isErrorEnabled()) {
            log.error("全局异常", e);
        }
        if (null != exceptionProcessors) {
            for (final ExceptionProcessor ep : exceptionProcessors) {
                if (!ep.support(e)) {
                    continue;
                }
                return ep.process(e);
            }
        }

        return RestResponse.error("程序出错啦！");
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public RestResponse<?> httpClientErrorException(HttpServletResponse resp, HttpClientErrorException e) {
        resp.setStatus(e.getStatusCode().value());
        if (log.isErrorEnabled()) {
            log.error("客户端异常，异常信息！", e);
        }
        return RestResponse.response(e.getStatusCode(), e.getMessage());
    }

    @ExceptionHandler(HttpStatusException.class)
    public RestResponse<?> httpStatusException(HttpServletResponse resp, HttpStatusException e) {
        resp.setStatus(e.getHttpStatus().value());
        if (log.isErrorEnabled()) {
            log.error("HttpStatus异常，异常信息！", e);
        }
        return RestResponse.response(e.getHttpStatus(), e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResponse<?> businessException(BusinessException e) {
        if (log.isErrorEnabled()) {
            log.error("业务处理异常，错误信息！", e);
        }
        return RestResponse.failure(e.getMessage());
    }

    @ExceptionHandler(FrameworkException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResponse<?> frameworkException(FrameworkException e) {
        if (log.isErrorEnabled()) {
            log.error("框架处理异常，错误信息！", e);
        }
        return RestResponse.failure(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse<?> validationException(ValidationException e) {
        if (log.isErrorEnabled()) {
            log.error("参数校验异常，错误信息！", e);
        }
        return RestResponse.response(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse<?> illegalArgumentException(IllegalArgumentException e) {
        if (log.isErrorEnabled()) {
            log.error("参数检查异常，错误信息！", e);
        }
        return RestResponse.response(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse<?> bindException(BindException e) {
        if (log.isErrorEnabled()) {
            log.error("参数绑定异常，错误信息！", e);
        }
        final StringBuilder sb = new StringBuilder();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            sb.append(error.getDefaultMessage()).append("\n");
        });
        return RestResponse.response(HttpStatus.BAD_REQUEST, sb);
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse<?> multipartException(MultipartException e) {
        if (log.isErrorEnabled()) {
            log.error("文件上传异常，错误信息！", e);
        }
        return RestResponse.response(HttpStatus.BAD_REQUEST, "文件上传出错：" + e.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse<?> maxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        if (log.isErrorEnabled()) {
            log.error("上传文件过大，错误信息！", e);
        }
        return RestResponse.response(HttpStatus.BAD_REQUEST,
                "上传文件过大：最大不超过" + FileUtil.readableSize(e.getMaxUploadSize()));
    }

}
