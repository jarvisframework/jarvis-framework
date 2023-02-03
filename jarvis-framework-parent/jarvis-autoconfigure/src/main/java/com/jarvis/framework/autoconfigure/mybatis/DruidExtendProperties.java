package com.jarvis.framework.autoconfigure.mybatis;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年3月10日
 */
@ConfigurationProperties(prefix = "spring.datasource.druid")
public class DruidExtendProperties implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6957501326095530616L;

    /** 自定义密码加密器 */
    private String passwordCallbackClassName;

    /**
     * @return the passwordCallbackClassName
     */
    public String getPasswordCallbackClassName() {
        return passwordCallbackClassName;
    }

    /**
     * @param passwordCallbackClassName the passwordCallbackClassName to set
     */
    public void setPasswordCallbackClassName(String passwordCallbackClassName) {
        this.passwordCallbackClassName = passwordCallbackClassName;
    }

}
