package com.jarvis.framework.security.validation.code.web;

import com.jarvis.framework.security.validation.code.ValidateCodeException;
import com.jarvis.framework.security.validation.code.ValidateCodeProcessorHolder;
import com.jarvis.framework.security.validation.code.config.ValidateCodeProperties;
import com.jarvis.framework.security.validation.code.constant.ValidateCodeTypeEnum;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ValidateCodeFilter extends OncePerRequestFilter {
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final ValidateCodeProcessorHolder validateCodeProcessorHolder;
    private final ValidateCodeProperties validateCodeProperties;
    private final Map<String, ValidateCodeTypeEnum> urlMap = new HashMap();
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public ValidateCodeFilter(AuthenticationFailureHandler authenticationFailureHandler, ValidateCodeProcessorHolder validateCodeProcessorHolder, ValidateCodeProperties validateCodeProperties) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.validateCodeProcessorHolder = validateCodeProcessorHolder;
        this.validateCodeProperties = validateCodeProperties;
        this.urlMap.put("/login/username", ValidateCodeTypeEnum.IMAGE);
        this.urlMap.put("/login/mobile", ValidateCodeTypeEnum.MOBILE);
        this.urlMap.put("/login/idcard", ValidateCodeTypeEnum.MOBILE);
        this.urlMap.put("/oauth2/token", ValidateCodeTypeEnum.IMAGE);
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (!this.validateCodeProperties.isEnabled()) {
            chain.doFilter(request, response);
        } else {
            ValidateCodeTypeEnum type = this.getValidateCodeType(request);
            if (type != null) {
                if (this.logger.isInfoEnabled()) {
                    this.logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
                }

                try {
                    this.validateCodeProcessorHolder.findValidateCodeProcessor(type).validate(new ServletWebRequest(request, response));
                    if (this.logger.isInfoEnabled()) {
                        this.logger.info("验证码校验通过");
                    }
                } catch (ValidateCodeException var6) {
                    this.authenticationFailureHandler.onAuthenticationFailure(request, response, var6);
                    return;
                }
            }

            chain.doFilter(request, response);
        }
    }

    private ValidateCodeTypeEnum getValidateCodeType(HttpServletRequest request) {
        ValidateCodeTypeEnum result = null;
        if (!"get".equalsIgnoreCase(request.getMethod())) {
            Set<String> urls = this.urlMap.keySet();
            Iterator var4 = urls.iterator();

            while(var4.hasNext()) {
                String url = (String)var4.next();
                if (this.pathMatcher.match(url, request.getRequestURI())) {
                    result = (ValidateCodeTypeEnum)this.urlMap.get(url);
                    break;
                }
            }
        }

        return result;
    }
}
