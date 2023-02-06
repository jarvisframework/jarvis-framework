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
import java.util.Map;
import java.util.Set;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月15日
 */
public class ValidateCodeFilter extends OncePerRequestFilter {

    /**
     * 验证码校验失败处理器
     */
    private final AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 系统中的校验码处理器
     */
    private final ValidateCodeProcessorHolder validateCodeProcessorHolder;

    private final ValidateCodeProperties validateCodeProperties;

    /**
     * 存放所有需要校验验证码的url
     */
    private final Map<String, ValidateCodeTypeEnum> urlMap = new HashMap<>();

    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * @param authenticationFailureHandler
     * @param validateCodeProcessorHolder
     */
    public ValidateCodeFilter(AuthenticationFailureHandler authenticationFailureHandler,
                              ValidateCodeProcessorHolder validateCodeProcessorHolder, ValidateCodeProperties validateCodeProperties) {
        super();
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.validateCodeProcessorHolder = validateCodeProcessorHolder;
        this.validateCodeProperties = validateCodeProperties;

        urlMap.put("/login/username", ValidateCodeTypeEnum.IMAGE);
        urlMap.put("/login/mobile", ValidateCodeTypeEnum.MOBILE);
        urlMap.put("/login/idcard", ValidateCodeTypeEnum.MOBILE);
        urlMap.put("/oauth2/token", ValidateCodeTypeEnum.IMAGE);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(
     * javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        if (!validateCodeProperties.isEnabled()) {
            chain.doFilter(request, response);
            return;
        }

        final ValidateCodeTypeEnum type = getValidateCodeType(request);
        if (type != null) {
            if (logger.isInfoEnabled()) {
                logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
            }
            try {
                validateCodeProcessorHolder.findValidateCodeProcessor(type)
                        .validate(new ServletWebRequest(request, response));
                if (logger.isInfoEnabled()) {
                    logger.info("验证码校验通过");
                }
            } catch (final ValidateCodeException exception) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
                return;
            }
        }

        chain.doFilter(request, response);

    }

    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     *
     * @param request
     * @return
     */
    private ValidateCodeTypeEnum getValidateCodeType(HttpServletRequest request) {
        ValidateCodeTypeEnum result = null;
        if (!"get".equalsIgnoreCase(request.getMethod())) {
            final Set<String> urls = urlMap.keySet();
            for (final String url : urls) {
                if (pathMatcher.match(url, request.getRequestURI())) {
                    result = urlMap.get(url);
                    break;
                }
            }
        }
        return result;
    }

}
