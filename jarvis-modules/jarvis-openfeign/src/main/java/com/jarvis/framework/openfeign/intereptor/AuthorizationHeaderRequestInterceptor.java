package com.jarvis.framework.openfeign.intereptor;

import com.jarvis.framework.constant.WebMvcConstant;
import com.jarvis.framework.openfeign.web.AuthorizationHeaderResolver;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年5月28日
 */
public class AuthorizationHeaderRequestInterceptor implements RequestInterceptor {

    /**
     *
     * @see feign.RequestInterceptor#apply(feign.RequestTemplate)
     */
    @Override
    public void apply(RequestTemplate template) {

        // 自动传送 access token
        final ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();

        if (ObjectUtils.isEmpty(attributes)) {
            return;
        }

        final HttpServletRequest request = attributes.getRequest();
        final String systemId = request.getHeader(WebMvcConstant.SYSTEM_HEADER_NAME);
        final String menuId = request.getHeader(WebMvcConstant.MENU_HEADER_NAME);
        final String token = AuthorizationHeaderResolver.resolve(request);
        if (null != systemId) {
            template.header(WebMvcConstant.SYSTEM_HEADER_NAME, systemId);
        }
        if (null != menuId) {
            template.header(WebMvcConstant.MENU_HEADER_NAME, menuId);
        }
        if (null != token) {
            template.header(HttpHeaders.AUTHORIZATION, token);
        }

    }

}
