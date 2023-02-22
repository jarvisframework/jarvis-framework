package com.jarvis.framework.security.validation.code.image;

import com.jarvis.framework.security.validation.code.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月15日
 */
public class ImageCode extends ValidateCode {

    /**
     *
     */
    private static final long serialVersionUID = 7463698259009092605L;

    private transient BufferedImage image;

    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code, expireIn);
        this.image = image;
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code, expireTime);
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
