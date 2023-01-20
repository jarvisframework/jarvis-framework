package com.jarvis.framework.security.validation.code.image;

import com.jarvis.framework.security.validation.code.AbstractValidateCodeProcessor;
import com.jarvis.framework.security.validation.code.ValidateCodeException;
import com.jarvis.framework.security.validation.code.ValidateCodeGenerator;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {
    private final ValidateCodeGenerator validateCodeGenerator;

    public ImageCodeProcessor(ValidateCodeGenerator validateCodeGenerator) {
        this.validateCodeGenerator = validateCodeGenerator;
    }

    public ValidateCodeGenerator getValidateCodeGenerator() {
        return this.validateCodeGenerator;
    }

    protected void send(ServletWebRequest request, ImageCode imageCode) {
        try {
            ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
        } catch (IOException var4) {
            throw new ValidateCodeException("生成图片验证失败", var4);
        }
    }
}
