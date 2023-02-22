package com.jarvis.framework.security.validation.code.mobile;

import com.jarvis.framework.security.validation.code.SmsCodeSender;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月19日
 */
public class DefaultSmsSender implements SmsCodeSender {

    /**
     *
     * @see com.jarvis.framework.security.validation.code.SmsCodeSender#send(java.lang.String, java.lang.String)
     */
    @Override
    public void send(String mobile, String code) {
        System.out.println(String.format("手机号[%s],验证码[%s]", mobile, code));
    }

}
