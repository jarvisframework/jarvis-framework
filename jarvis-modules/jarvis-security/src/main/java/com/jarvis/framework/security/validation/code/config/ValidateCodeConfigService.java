package com.jarvis.framework.security.validation.code.config;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年7月28日
 */
public class ValidateCodeConfigService {

    private final ValidateCodeProperties properties;

    public ValidateCodeConfigService(ValidateCodeProperties properties) {
        this.properties = properties;
    }

    public void setEnabled(boolean enabled) {
        properties.setEnabled(enabled);
    }

}

