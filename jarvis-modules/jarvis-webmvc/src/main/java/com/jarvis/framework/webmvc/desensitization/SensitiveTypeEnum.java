package com.jarvis.framework.webmvc.desensitization;

/**
 * 脱敏类型
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月16日
 */
public enum SensitiveTypeEnum {
    /**
     * 中文名
     */
    CHINESE_NAME,
    /**
     * 身份证号
     */
    ID_CARD,
    /**
     * 座机号
     */
    FIXED_PHONE,
    /**
     * 手机号
     */
    MOBILE_PHONE,
    /**
     * 地址
     */
    ADDRESS,
    /**
     * 电子邮件
     */
    EMAIL,
    /**
     * 银行卡
     */
    BANK_CARD,
    /**
     * 密码
     */
    PASSWORD,
    /**
     * 车牌号
     */
    CAR_NUMBER,
    /**
     * 密钥
     */
    KEY;
}
