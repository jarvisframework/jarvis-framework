package com.jarvis.framework.security.validation.code.constant;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月15日
 */
public enum ValidateCodeTypeEnum {

    /**
     * 短信验证码
     */
    MOBILE {

        @Override
        public String getParamNameOnValidate() {
            return "mobile";
        }
    },
    /**
     * 图片验证码
     */
    IMAGE {

        @Override
        public String getParamNameOnValidate() {
            return "image";
        }
    };

    /**
     * 校验时从请求中获取的参数的名字
     *
     * @return
     */
    public abstract String getParamNameOnValidate();
}
