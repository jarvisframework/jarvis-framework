package com.jarvis.framework.security.validation.code.config;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月15日
 */
public class ValidateCodeProperties {

    /**
     * 是否启用验证码
     */
    private boolean enabled;

    /**
     * 图片验证码
     */
    private ImageCodeProperties image;

    /**
     * 手机验证
     */
    private MobileCodeProperties mobile;

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the image
     */
    public ImageCodeProperties getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(ImageCodeProperties image) {
        this.image = image;
    }

    /**
     * @return the mobile
     */
    public MobileCodeProperties getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(MobileCodeProperties mobile) {
        this.mobile = mobile;
    }

}
