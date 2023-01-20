package com.jarvis.framework.security.validation.code;

import com.jarvis.framework.security.validation.code.constant.ValidateCodeTypeEnum;

import java.util.Map;

public class ValidateCodeProcessorHolder {
    private final Map<String, ValidateCodeProcessor> validateCodeProcessors;

    public ValidateCodeProcessorHolder(Map<String, ValidateCodeProcessor> validateCodeProcessors) {
        this.validateCodeProcessors = validateCodeProcessors;
    }

    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeTypeEnum type) {
        return this.findValidateCodeProcessor(type.toString().toLowerCase());
    }

    public ValidateCodeProcessor findValidateCodeProcessor(String type) {
        String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
        ValidateCodeProcessor processor = (ValidateCodeProcessor)this.validateCodeProcessors.get(name);
        if (processor == null) {
            throw new ValidateCodeException("验证码处理器" + name + "不存在");
        } else {
            return processor;
        }
    }
}
