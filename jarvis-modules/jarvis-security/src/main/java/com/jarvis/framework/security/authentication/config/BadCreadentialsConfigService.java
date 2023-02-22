package com.jarvis.framework.security.authentication.config;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年7月28日
 */
public class BadCreadentialsConfigService {

    private final BadCreadentialsProperties properties;

    public BadCreadentialsConfigService(BadCreadentialsProperties properties) {
        this.properties = properties;
    }

    /**
     * 是否开启连续出错检查
     *
     * @param enabled false表示不开启
     */
    public void setEnabled(boolean enabled) {
        properties.setEnabled(enabled);
    }

    /**
     * 连续出错次数
     *
     * @param count the count to set
     */
    public void setCount(int count) {
        properties.setCount(count);
    }

    /**
     * 锁定时间（单位分钟） 0表示永久锁定
     *
     * @param timeout the timeout to set
     */
    public void setTimeout(int timeout) {
        properties.setTimeout(timeout);
    }

}
