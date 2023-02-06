package com.jarvis.framework.security.validation.code;

import com.jarvis.framework.security.validation.code.constant.ValidateCodeTypeEnum;

import java.util.Map;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月15日
 */
public class ValidateCodeProcessorHolder {

    private final Map<String, ValidateCodeProcessor> validateCodeProcessors;

    public ValidateCodeProcessorHolder(Map<String, ValidateCodeProcessor> validateCodeProcessors) {
        this.validateCodeProcessors = validateCodeProcessors;
    }

    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeTypeEnum type) {
        return findValidateCodeProcessor(type.toString().toLowerCase());
    }

    public ValidateCodeProcessor findValidateCodeProcessor(String type) {
        final String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
        final ValidateCodeProcessor processor = validateCodeProcessors.get(name);
        if (processor == null) {
            throw new ValidateCodeException("验证码处理器" + name + "不存在");
        }
        return processor;
    }

}
