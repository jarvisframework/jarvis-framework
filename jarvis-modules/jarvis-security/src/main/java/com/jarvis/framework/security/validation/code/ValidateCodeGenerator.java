package com.jarvis.framework.security.validation.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月15日
 */
public interface ValidateCodeGenerator {

    ValidateCode generate(ServletWebRequest request);
}
