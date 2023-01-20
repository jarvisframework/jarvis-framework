package com.jarvis.framework.mybatis.handler;

public interface EntityAutoFillingHandler {
    void insert(BaseIdPrimaryKeyEntity<?> var1);

    void update(BaseIdPrimaryKeyEntity<?> var1);
}
