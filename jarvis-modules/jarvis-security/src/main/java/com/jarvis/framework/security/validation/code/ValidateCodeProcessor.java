package com.jarvis.framework.security.validation.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月15日
 */
public interface ValidateCodeProcessor {

    /**
     * 验证码放入session时的前缀
     */
    String SESSION_VALIDATE_CODE = "session_validate_code";

    /**
     * 设备ID参数名称
     */
    String DEVICE_ID_PARAMETER = "deviceId";

    /**
     * 创建校验码
     *
     * @param request
     */
    void create(ServletWebRequest request);

    /**
     * 校验验证码
     *
     * @param servletWebRequest
     * @throws Exception
     */
    void validate(ServletWebRequest servletWebRequest);

    /**
     * 对应的验证码处理器
     *
     * @return
     */
    ValidateCodeGenerator getValidateCodeGenerator();

    /**
     * 设置验证码存储器
     *
     * @param validateCodeStoreService 验证码存储器
     */
    void setValidateCodeStoreService(ValidateCodeStoreService validateCodeStoreService);

}
