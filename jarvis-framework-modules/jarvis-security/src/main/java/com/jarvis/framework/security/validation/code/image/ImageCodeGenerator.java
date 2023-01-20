package com.jarvis.framework.security.validation.code.image;

import com.jarvis.framework.security.validation.code.ValidateCodeGenerator;
import com.jarvis.framework.security.validation.code.config.ImageCodeProperties;
import com.jarvis.framework.util.RandomStringUtil;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ImageCodeGenerator implements ValidateCodeGenerator {
    private final ImageCodeProperties imageCodeProperties;

    public ImageCodeGenerator(ImageCodeProperties imageCodeProperties) {
        this.imageCodeProperties = imageCodeProperties;
    }

    public ImageCode generate(ServletWebRequest request) {
        int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width", this.imageCodeProperties.getWidth());
        int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height", this.imageCodeProperties.getHeight());
        int len = this.imageCodeProperties.getLength();
        BufferedImage image = new BufferedImage(width, height, 1);
        Graphics2D g = image.createGraphics();
        Random random = ThreadLocalRandom.current();
        g.setColor(this.getRandColor(128, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Georgia", 2, 22));

        for(int i = 0; i < 256; ++i) {
            int x = random.nextInt(width);
            i = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.setColor(this.getRandColor(160, 200));
            g.drawLine(x, i, x + xl, i + yl);
        }

        String sRand = RandomStringUtil.random(len);
        char[] chars = sRand.toCharArray();

        for(int i = 0; i < len; ++i) {
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(0.7853981633974483D * random.nextDouble() * (double)(random.nextBoolean() ? 1 : -1), (double)(width / len * i + 11), (double)(height / 2));
            g.setTransform(affine);
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.setFont(new Font("Georgia", random.nextInt(4), 22));
            g.drawChars(chars, i, 1, (width - 10) / len * i + 5, height / 2 + 11 - 5);
        }

        g.dispose();
        return new ImageCode(image, sRand, this.imageCodeProperties.getExpireIn());
    }

    private Color getRandColor(int fc, int bc) {
        Random random = ThreadLocalRandom.current();
        if (fc > 255) {
            fc = 255;
        }

        if (bc > 255) {
            bc = 255;
        }

        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
