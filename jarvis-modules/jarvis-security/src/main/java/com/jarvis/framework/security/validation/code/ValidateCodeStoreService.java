package com.jarvis.framework.security.validation.code;

import org.springframework.web.context.request.RequestAttributes;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月26日
 */
public interface ValidateCodeStoreService {

    /**
     * 存储验证码
     *
     * @param request 请求对象
     * @param name 验证码Key
     * @param value 验证码值
     */
    void store(RequestAttributes request, String name, Object value);

    /**
     * 获取验证码
     *
     * @param request 请求对象
     * @param name 验证码Key
     * @return
     */
    Object get(RequestAttributes request, String name);

    /**
     * 移除验证码
     *
     * @param request 请求对象
     * @param name 验证码Key
     */
    void remove(RequestAttributes request, String name);

}
