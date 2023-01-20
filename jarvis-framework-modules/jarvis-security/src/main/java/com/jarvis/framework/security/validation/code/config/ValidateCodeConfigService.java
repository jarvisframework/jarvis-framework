package com.jarvis.framework.security.validation.code.config;

public class ValidateCodeConfigService {

    private final ValidateCodeProperties properties;

    public ValidateCodeConfigService(ValidateCodeProperties properties) {
        this.properties = properties;
    }

    public void setEnabled(boolean enabled) {
        this.properties.setEnabled(enabled);
    }

}
