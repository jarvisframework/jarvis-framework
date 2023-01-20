package com.jarvis.framework.security.validation.code;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeProcessor {
    String SESSION_VALIDATE_CODE = "session_validate_code";
    String DEVICE_ID_PARAMETER = "deviceId";

    void create(ServletWebRequest var1);

    void validate(ServletWebRequest var1);

    ValidateCodeGenerator getValidateCodeGenerator();

    void setValidateCodeStoreService(ValidateCodeStoreService var1);
}
