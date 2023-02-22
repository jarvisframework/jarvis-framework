package com.jarvis.framework.security.validation.code;

import com.jarvis.framework.security.validation.code.web.SessionValidateCodeService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月15日
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    /**
     * 操作session的工具类
     */
    private ValidateCodeStoreService validateCodeStoreService = new SessionValidateCodeService();

    /*
     *
     */
    @Override
    public void create(ServletWebRequest request) {
        final C validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    /**
     * 生成校验码
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) {
        return (C) getValidateCodeGenerator().generate(request);
    }

    /**
     * 保存校验码
     *
     * @param request
     * @param validateCode
     */
    protected void save(ServletWebRequest request, C validateCode) {
        validateCodeStoreService.store(request, getValidateKey(request), validateCode);
    }

    /**
     * 发送校验码，由子类实现
     *
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, C validateCode);

    protected String getValidateKey(ServletWebRequest request) {
        final ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        final String deviceId = servletWebRequest.getParameter(DEVICE_ID_PARAMETER);
        if (!StringUtils.hasLength(deviceId)) {
            throw new ValidateCodeException(String.format("设备参数[%s]不能为空", DEVICE_ID_PARAMETER));
        }
        return String.format("%s:%s", SESSION_VALIDATE_CODE, deviceId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void validate(ServletWebRequest request) {

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "code");
        } catch (final ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (!StringUtils.hasLength(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }

        final String validateKey = getValidateKey(request);

        final C codeInStore = (C) validateCodeStoreService.get(request, validateKey);

        if (codeInStore == null) {
            throw new ValidateCodeException("验证码不存在");
        }

        if (codeInStore.isExpried()) {
            validateCodeStoreService.remove(request, validateKey);
            throw new ValidateCodeException("验证码已过期");
        }

        if (!codeInRequest.equals(codeInStore.getCode())) {
            throw new ValidateCodeException("验证码不匹配");
        }

        validateCodeStoreService.remove(request, validateKey);
    }

    @Override
    public void setValidateCodeStoreService(ValidateCodeStoreService validateCodeStoreService) {
        this.validateCodeStoreService = validateCodeStoreService;
    }

}
