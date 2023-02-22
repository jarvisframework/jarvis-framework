package com.jarvis.framework.security.constant;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月25日
 */
public abstract class SecurityConstant {

    /**
     * 全局忽略URL
     */
    public static final String[] GLOBAL_IGNORE_URLS = new String[] {
            "/index.html**",
            "/assets/css/**", "/assets/js/**", "/assets/img/**", "/assets/fonts/**",
            "/favicon.ico",
            "/swagger-ui/**", "/swagger-resources", "/swagger-resources/**",
            "/v2/api-docs", "/v2/api-docs/**",
            "/v3/api-docs", "/v3/api-docs/**",
            "/validate-code/image", "/validate-code/mobile"
    };

}
