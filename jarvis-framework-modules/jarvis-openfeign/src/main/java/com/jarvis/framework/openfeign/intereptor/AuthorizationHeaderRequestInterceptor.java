package com.jarvis.framework.openfeign.intereptor;

import com.jarvis.framework.openfeign.web.AuthorizationHeaderResolver;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class AuthorizationHeaderRequestInterceptor implements RequestInterceptor {
    public AuthorizationHeaderRequestInterceptor() {
    }

    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if (!ObjectUtils.isEmpty(attributes)) {
            HttpServletRequest request = attributes.getRequest();
            String systemId = request.getHeader("X-System-Id");
            String menuId = request.getHeader("X-Menu-Id");
            String token = AuthorizationHeaderResolver.resolve(request);
            if (null != systemId) {
                template.header("X-System-Id", new String[]{systemId});
            }

            if (null != menuId) {
                template.header("X-Menu-Id", new String[]{menuId});
            }

            if (null != token) {
                template.header("Authorization", new String[]{token});
            }

        }
    }
}
