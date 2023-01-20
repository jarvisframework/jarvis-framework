package com.jarvis.framework.security.validation.code;

import com.jarvis.framework.security.validation.code.web.SessionValidateCodeService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {
    private ValidateCodeStoreService validateCodeStoreService = new SessionValidateCodeService();

    public void create(ServletWebRequest request) {
        C validateCode = this.generate(request);
        this.save(request, validateCode);
        this.send(request, validateCode);
    }

    private C generate(ServletWebRequest request) {
        return null/*this.getValidateCodeGenerator().generate(request)*/;
    }

    protected void save(ServletWebRequest request, C validateCode) {
        this.validateCodeStoreService.store(request, this.getValidateKey(request), validateCode);
    }

    protected abstract void send(ServletWebRequest var1, C var2);

    protected String getValidateKey(ServletWebRequest request) {
        String deviceId = request.getParameter("deviceId");
        if (!StringUtils.hasLength(deviceId)) {
            throw new ValidateCodeException(String.format("设备参数[%s]不能为空", "deviceId"));
        } else {
            return String.format("%s:%s", "session_validate_code", deviceId);
        }
    }

    public void validate(ServletWebRequest request) {
        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "code");
        } catch (ServletRequestBindingException var5) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (!StringUtils.hasLength(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        } else {
            String validateKey = this.getValidateKey(request);
            ValidateCode codeInStore = (ValidateCode)this.validateCodeStoreService.get(request, validateKey);
            if (codeInStore == null) {
                throw new ValidateCodeException("验证码不存在");
            } else if (codeInStore.isExpried()) {
                this.validateCodeStoreService.remove(request, validateKey);
                throw new ValidateCodeException("验证码已过期");
            } else if (!codeInRequest.equals(codeInStore.getCode())) {
                throw new ValidateCodeException("验证码不匹配");
            } else {
                this.validateCodeStoreService.remove(request, validateKey);
            }
        }
    }

    public void setValidateCodeStoreService(ValidateCodeStoreService validateCodeStoreService) {
        this.validateCodeStoreService = validateCodeStoreService;
    }
}
