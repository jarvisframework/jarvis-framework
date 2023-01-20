package com.jarvis.framework.security.validation.code.mobile;

import com.jarvis.framework.security.validation.code.AbstractValidateCodeProcessor;
import com.jarvis.framework.security.validation.code.SmsCodeSender;
import com.jarvis.framework.security.validation.code.ValidateCode;
import com.jarvis.framework.security.validation.code.ValidateCodeException;
import com.jarvis.framework.security.validation.code.ValidateCodeGenerator;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

public class MobileCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {
    private final SmsCodeSender smsCodeSender;
    private final ValidateCodeGenerator mobileCodeGenerator;

    public MobileCodeProcessor(SmsCodeSender smsCodeSender, ValidateCodeGenerator mobileCodeGenerator) {
        this.smsCodeSender = smsCodeSender;
        this.mobileCodeGenerator = mobileCodeGenerator;
    }

    public ValidateCodeGenerator getValidateCodeGenerator() {
        return this.mobileCodeGenerator;
    }

    protected void send(ServletWebRequest request, ValidateCode validateCode) {
        try {
            String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
            this.smsCodeSender.send(mobile, validateCode.getCode());
        } catch (ServletRequestBindingException var4) {
            throw new ValidateCodeException("发送手机验证码证失败", var4);
        }
    }

    protected String getValidateKey(ServletWebRequest request) {
        try {
            String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
            if (!StringUtils.hasLength(mobile)) {
                throw new ValidateCodeException("手机号不能为空");
            } else {
                return String.format("%s:%s", "session_validate_code", mobile);
            }
        } catch (ServletRequestBindingException var3) {
            throw new ValidateCodeException("发送手机验证码证失败", var3);
        }
    }
}
