package com.jarvis.framework.autoconfigure.webmvc.access;

import com.jarvis.framework.constant.WebMvcConstant;
import com.jarvis.framework.web.exception.HttpStatusException;
import com.jarvis.framework.webmvc.util.WebUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年11月2日
 */
@Aspect
public class JarvisInnerAccessAspect {

    private static Logger log = LoggerFactory.getLogger(JarvisInnerAccessAspect.class);

    @Before("@within(com.jarvis.framework.annotation.InnerAccess) || @annotation(com.jarvis.framework.annotation.InnerAccess)")
    public void execute(JoinPoint point) throws Throwable {
        final String value = WebUtil.getHeader(WebMvcConstant.FEIGN_HEADER_NAME);
        if (null == value) {
            log.warn("访问接口 {} 没有权限！", point.getSignature().getName());
            throw HttpStatusException.create(HttpStatus.METHOD_NOT_ALLOWED, "禁止访问");
        }
    }

}
