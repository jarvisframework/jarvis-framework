package com.jarvis.framework.security.service;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface HttpSecurityProcessor {
    void process(HttpSecurity var1);
}
