package com.jarvis.framework.security.validation.code.mobile;

import com.jarvis.framework.security.validation.code.SmsCodeSender;

public class DefaultSmsSender implements SmsCodeSender {

    public void send(String mobile, String code) {
        System.out.println(String.format("手机号[%s],验证码[%s]", mobile, code));
    }
}
