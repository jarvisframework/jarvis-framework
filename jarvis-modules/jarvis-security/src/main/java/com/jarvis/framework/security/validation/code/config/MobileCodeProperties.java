package com.jarvis.framework.security.validation.code.config;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月15日
 */
public class MobileCodeProperties {

    private int length = 6;

    /**
     * 过期单位（秒）
     */
    private int expireIn = 60;

    public int getLength() {
        return length;
    }

    public void setLength(int lenght) {
        this.length = lenght;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

}
