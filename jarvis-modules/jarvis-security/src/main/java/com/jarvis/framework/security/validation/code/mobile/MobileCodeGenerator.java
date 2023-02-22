package com.jarvis.framework.security.validation.code.mobile;

import com.jarvis.framework.security.validation.code.ValidateCode;
import com.jarvis.framework.security.validation.code.ValidateCodeGenerator;
import com.jarvis.framework.security.validation.code.config.MobileCodeProperties;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月15日
 */
public class MobileCodeGenerator implements ValidateCodeGenerator {

    private final MobileCodeProperties mobileCodeProperties;

    public MobileCodeGenerator(MobileCodeProperties mobileCodeProperties) {
        this.mobileCodeProperties = mobileCodeProperties;
    }

    /**
     *
     * @see com.jarvis.framework.security.validation.code.ValidateCodeGenerator#generate(org.springframework.web.context.request.ServletWebRequest)
     */
    @Override
    public ValidateCode generate(ServletWebRequest request) {
        final String code = randomNumber(mobileCodeProperties.getLength());
        return new ValidateCode(code, mobileCodeProperties.getExpireIn());
    }

    private String randomNumber(int len) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

}
