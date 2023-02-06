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

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月15日
 */
public class ImageCodeGenerator implements ValidateCodeGenerator {

    private final ImageCodeProperties imageCodeProperties;

    public ImageCodeGenerator(ImageCodeProperties imageCodeProperties) {
        this.imageCodeProperties = imageCodeProperties;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.imooc.security.core.validate.code.ValidateCodeGenerator#generate(org.
     * springframework.web.context.request.ServletWebRequest)
     */
    @Override
    public ImageCode generate(ServletWebRequest request) {
        final int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width",
                imageCodeProperties.getWidth());
        final int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height",
                imageCodeProperties.getHeight());
        final int len = imageCodeProperties.getLength();
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        final Graphics2D g = image.createGraphics();

        final Random random = ThreadLocalRandom.current();
        final int fontSize = 22;

        g.setColor(getRandColor(128, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Georgia", Font.ITALIC, fontSize));
        for (int i = 0; i < 256; i++) {
            final int x = random.nextInt(width);
            final int y = random.nextInt(height);
            final int xl = random.nextInt(12);
            final int yl = random.nextInt(12);
            g.setColor(getRandColor(160, 200));
            g.drawLine(x, y, x + xl, y + yl);
        }

        final String sRand = RandomStringUtil.random(len);
        final char[] chars = sRand.toCharArray();
        for (int i = 0; i < len; i++) {
            final AffineTransform affine = new AffineTransform();
            affine.setToRotation(
                    Math.PI / 4 * random.nextDouble() * (random.nextBoolean() ? 1 : -1),
                    (width / len) * i + fontSize / 2,
                    height / 2);
            g.setTransform(affine);
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.setFont(new Font("Georgia", random.nextInt(4), fontSize));
            g.drawChars(chars, i, 1, ((width - 10) / len) * i + 5, height / 2 + fontSize / 2 - 5);
        }

        g.dispose();

        return new ImageCode(image, sRand, imageCodeProperties.getExpireIn());
    }

    /**
     * 生成随机背景条纹
     *
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {
        final Random random = ThreadLocalRandom.current();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        final int r = fc + random.nextInt(bc - fc);
        final int g = fc + random.nextInt(bc - fc);
        final int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
