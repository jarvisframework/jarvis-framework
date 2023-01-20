package com.jarvis.framework.security.validation.code.web;

import com.jarvis.framework.security.validation.code.ValidateCodeException;
import com.jarvis.framework.security.validation.code.ValidateCodeProcessorHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ValidateCodeEndpoint {
    private final ValidateCodeProcessorHolder validateCodeProcessorHolder;

    public ValidateCodeEndpoint(ValidateCodeProcessorHolder validateCodeProcessorHolder) {
        this.validateCodeProcessorHolder = validateCodeProcessorHolder;
    }

    @GetMapping({"/validate-code/{type}"})
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {
        try {
            this.validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
        } catch (ValidateCodeException var5) {
            throw var5;
        } catch (Exception var6) {
            throw new ValidateCodeException("获取验证码出错", var6);
        }
    }
}
