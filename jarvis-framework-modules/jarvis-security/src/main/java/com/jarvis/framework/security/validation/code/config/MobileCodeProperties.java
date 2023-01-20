package com.jarvis.framework.security.validation.code.config;

public class MobileCodeProperties {

    private int length = 6;

    private int expireIn = 60;

    public int getLength() {
        return this.length;
    }

    public void setLength(int lenght) {
        this.length = lenght;
    }

    public int getExpireIn() {
        return this.expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }
}
