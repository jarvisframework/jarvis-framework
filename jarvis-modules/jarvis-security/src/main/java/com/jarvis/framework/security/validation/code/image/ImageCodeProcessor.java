package com.jarvis.framework.security.validation.code.image;

import com.jarvis.framework.security.validation.code.AbstractValidateCodeProcessor;
import com.jarvis.framework.security.validation.code.ValidateCodeException;
import com.jarvis.framework.security.validation.code.ValidateCodeGenerator;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月15日
 */
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    private final ValidateCodeGenerator validateCodeGenerator;

    public ImageCodeProcessor(ValidateCodeGenerator validateCodeGenerator) {
        this.validateCodeGenerator = validateCodeGenerator;
    }

    /**
     *
     * @see com.jarvis.framework.security.validation.code.ValidateCodeProcessor#getValidateCodeGenerator()
     */
    @Override
    public ValidateCodeGenerator getValidateCodeGenerator() {
        return this.validateCodeGenerator;
    }

    /**
     *
     * @see com.jarvis.framework.security.validation.code.AbstractValidateCodeProcessor#send(org.springframework.web.context.request.ServletWebRequest,
     *      com.jarvis.framework.security.validation.code.ValidateCode)
     */
    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) {
        try {
            ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
        } catch (final IOException e) {
            throw new ValidateCodeException("生成图片验证失败", e);
        }
    }

}
