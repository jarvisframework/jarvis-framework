package com.jarvis.framework.security.validation.code.mobile;

import com.jarvis.framework.security.validation.code.ValidateCode;
import com.jarvis.framework.security.validation.code.ValidateCodeGenerator;
import com.jarvis.framework.security.validation.code.config.MobileCodeProperties;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.ThreadLocalRandom;

public class MobileCodeGenerator implements ValidateCodeGenerator {
    private final MobileCodeProperties mobileCodeProperties;

    public MobileCodeGenerator(MobileCodeProperties mobileCodeProperties) {
        this.mobileCodeProperties = mobileCodeProperties;
    }

    public ValidateCode generate(ServletWebRequest request) {
        String code = this.randomNumber(this.mobileCodeProperties.getLength());
        return new ValidateCode(code, this.mobileCodeProperties.getExpireIn());
    }

    private String randomNumber(int len) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < len; ++i) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }
}
