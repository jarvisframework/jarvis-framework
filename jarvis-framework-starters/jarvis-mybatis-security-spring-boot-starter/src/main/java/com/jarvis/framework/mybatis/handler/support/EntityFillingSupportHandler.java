package com.jarvis.framework.mybatis.handler.support;


import com.jarvis.framework.core.entity.BaseIdPrimaryKeyEntity;

public interface EntityFillingSupportHandler {
    boolean support(BaseIdPrimaryKeyEntity<?> var1);

    void insert(BaseIdPrimaryKeyEntity<?> var1);

    void update(BaseIdPrimaryKeyEntity<?> var1);
}
