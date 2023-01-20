package com.jarvis.framework.security.validation.code.config;

public class ValidateCodeProperties {
    private boolean enabled;
    private ImageCodeProperties image;
    private MobileCodeProperties mobile;


    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ImageCodeProperties getImage() {
        return this.image;
    }

    public void setImage(ImageCodeProperties image) {
        this.image = image;
    }

    public MobileCodeProperties getMobile() {
        return this.mobile;
    }

    public void setMobile(MobileCodeProperties mobile) {
        this.mobile = mobile;
    }
}
