package com.jarvis.framework.security.password;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface DelegatePasswordEncoder {

    String encoderId();

    PasswordEncoder passwordEncoder();
}
