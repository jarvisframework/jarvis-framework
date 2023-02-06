package com.jarvis.framework.security.validation.code.web;

import com.jarvis.framework.security.validation.code.ValidateCodeException;
import com.jarvis.framework.security.validation.code.ValidateCodeProcessorHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月16日
 */
@RestController
//@ConditionalOnProperty(prefix = "spring.security.validate-code", name = "enabled", havingValue = "true")
public class ValidateCodeEndpoint {

    private final ValidateCodeProcessorHolder validateCodeProcessorHolder;

    public ValidateCodeEndpoint(ValidateCodeProcessorHolder validateCodeProcessorHolder) {
        this.validateCodeProcessorHolder = validateCodeProcessorHolder;
    }

    @GetMapping("/validate-code/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type)
            throws Exception {
        try {
            validateCodeProcessorHolder.findValidateCodeProcessor(type)
                    .create(new ServletWebRequest(request, response));
        } catch (final ValidateCodeException e) {
            throw e;
        } catch (final Exception e) {
            throw new ValidateCodeException("获取验证码出错", e);
        }
    }

}
