package com.jarvis.framework.security.validation.code.constant;

public enum ValidateCodeTypeEnum {
    MOBILE {
        public String getParamNameOnValidate() {
            return "mobile";
        }
    },
    IMAGE {
        public String getParamNameOnValidate() {
            return "image";
        }
    };

    private ValidateCodeTypeEnum() {
    }

    public abstract String getParamNameOnValidate();
}
