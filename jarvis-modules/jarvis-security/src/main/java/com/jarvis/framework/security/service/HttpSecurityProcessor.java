package com.jarvis.framework.security.service;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * 处理定制化HttpSecurity配置
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月30日
 */
public interface HttpSecurityProcessor {

    /**
     * HttpSecurity处理
     *
     * @param http
     */
    void process(HttpSecurity http);

}
