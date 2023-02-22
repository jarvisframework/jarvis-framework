package com.jarvis.framework.security.validation.code;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月19日
 */
public interface SmsCodeSender {

    /**
     * 发送短信
     *
     * @param mobile 手机号
     * @param code 验证码
     */
    void send(String mobile, String code);

}
