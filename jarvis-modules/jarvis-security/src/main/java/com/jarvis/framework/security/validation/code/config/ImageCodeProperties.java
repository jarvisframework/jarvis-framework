package com.jarvis.framework.security.validation.code.config;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月15日
 */
public class ImageCodeProperties extends MobileCodeProperties {

    public ImageCodeProperties() {
        setLength(4);
    }

    private int width = 80;

    private int height = 26;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
