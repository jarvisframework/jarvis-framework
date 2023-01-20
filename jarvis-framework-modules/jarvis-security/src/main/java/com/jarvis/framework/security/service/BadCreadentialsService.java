package com.jarvis.framework.security.service;

public interface BadCreadentialsService {

    void checkAccountNonLocked(String var1);

    int increaseErrorCount(String var1);

    void releaseAccountLocked(String var1);
}
