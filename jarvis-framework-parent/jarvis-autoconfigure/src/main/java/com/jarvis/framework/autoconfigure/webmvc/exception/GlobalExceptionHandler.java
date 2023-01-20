package com.jarvis.framework.autoconfigure.webmvc.exception;

import com.jarvis.framework.webmvc.web.exception.handler.ExceptionProcessor;
import com.jarvis.framework.autoconfigure.mybatis.DruidExtendProperties;
import com.jarvis.framework.autoconfigure.security.ArchiveSecurityConfiguration;
import com.jarvis.framework.core.exception.BusinessException;
import com.jarvis.framework.core.exception.FrameworkException;
import com.jarvis.framework.web.rest.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Iterator;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired(
        required = false
    )
    private List<ExceptionProcessor> exceptionProcessors;
    private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse<?> bindException(BindException a) {
        if (log.isErrorEnabled()) {
            log.error(ArchiveSecurityConfiguration.oOoOOo("史敖绡宼弲帞＼锿诟俇恟＼K["), a);
        }

        StringBuilder var2 = new StringBuilder();
        a.getBindingResult().getFieldErrors().forEach((ax) -> {
            var2.append(ax.getDefaultMessage()).append(DruidExtendProperties.oOoOOo(";"));
        });
        return RestResponse.response(HttpStatus.BAD_REQUEST, var2);
    }

    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResponse<?> businessException(BusinessException a) {
        if (log.isErrorEnabled()) {
            log.error(DruidExtendProperties.oOoOOo("乎劐奐琷彖帉ｘ锨讻俐总Ｋ/L"), a);
        }

        return RestResponse.failure(a.getMessage());
    }

    @ExceptionHandler({FrameworkException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResponse<?> frameworkException(FrameworkException a) {
        if (log.isErrorEnabled()) {
            log.error(ArchiveSecurityConfiguration.oOoOOo("桶析头琠弲帞＼锿诟俇恟＼K["), a);
        }

        return RestResponse.failure(a.getMessage());
    }

    public GlobalExceptionHandler() {
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse<?> illegalArgumentException(IllegalArgumentException a) {
        if (log.isErrorEnabled()) {
            log.error(DruidExtendProperties.oOoOOo("厖敁梔柔彖帉ｘ锨讻俐总Ｋ/L"), a);
        }

        return RestResponse.response(HttpStatus.BAD_REQUEST, a.getMessage());
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResponse<?> globalException(Exception a) {
        if (log.isErrorEnabled()) {
            log.error(DruidExtendProperties.oOoOOo("儼山彖帉"), a);
        }

        Iterator var2;
        if (null != a.exceptionProcessors) {
            for(Iterator var10000 = var2 = a.exceptionProcessors.iterator(); var10000.hasNext(); var10000 = var2) {
                ExceptionProcessor var3;
                if ((var3 = (ExceptionProcessor)var2.next()).support(a)) {
                    return var3.process(a);
                }
            }
        }

        return RestResponse.error(ArchiveSecurityConfiguration.oOoOOo("稭庿凜锩啀"));
    }
}
