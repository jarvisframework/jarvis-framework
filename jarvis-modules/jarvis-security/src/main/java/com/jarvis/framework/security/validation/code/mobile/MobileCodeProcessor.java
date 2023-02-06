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

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月19日
 */
public class MobileCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    /**
     * 短信验证码发送器
     */
    private final SmsCodeSender smsCodeSender;

    private final ValidateCodeGenerator mobileCodeGenerator;

    /**
     * @param smsCodeSender
     * @param mobileCodeGenerator
     */
    public MobileCodeProcessor(SmsCodeSender smsCodeSender, ValidateCodeGenerator mobileCodeGenerator) {
        super();
        this.smsCodeSender = smsCodeSender;
        this.mobileCodeGenerator = mobileCodeGenerator;
    }

    /**
     *
     * @see com.jarvis.framework.security.validation.code.ValidateCodeProcessor#getValidateCodeGenerator()
     */
    @Override
    public ValidateCodeGenerator getValidateCodeGenerator() {
        return mobileCodeGenerator;
    }

    /**
     *
     * @see com.jarvis.framework.security.validation.code.AbstractValidateCodeProcessor#send(org.springframework.web.context.request.ServletWebRequest,
     *      com.jarvis.framework.security.validation.code.ValidateCode)
     */
    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) {
        try {
            final String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
            smsCodeSender.send(mobile, validateCode.getCode());
        } catch (final ServletRequestBindingException e) {
            throw new ValidateCodeException("发送手机验证码证失败", e);
        }
    }

    /**
     *
     * @see com.jarvis.framework.security.validation.code.AbstractValidateCodeProcessor#getValidateKey(org.springframework.web.context.request.ServletWebRequest)
     */
    @Override
    protected String getValidateKey(ServletWebRequest request) {
        try {
            final String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
            if (!StringUtils.hasLength(mobile)) {
                throw new ValidateCodeException("手机号不能为空");
            }
            return String.format("%s:%s", SESSION_VALIDATE_CODE, mobile);
        } catch (final ServletRequestBindingException e) {
            throw new ValidateCodeException("发送手机验证码证失败", e);
        }
    }

}
