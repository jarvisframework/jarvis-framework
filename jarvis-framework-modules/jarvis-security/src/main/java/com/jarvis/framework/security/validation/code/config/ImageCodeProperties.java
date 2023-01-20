package com.jarvis.framework.security.validation.code.config;

public class ImageCodeProperties extends MobileCodeProperties {

    private int width = 80;

    private int height = 26;

    public ImageCodeProperties() {
        this.setLength(4);
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
