package com.jarvis.framework.mybatis.handler;

import com.jarvis.framework.core.entity.BaseIdPrimaryKeyEntity;

public interface EntityAutoFillingHandler {
    void insert(BaseIdPrimaryKeyEntity<?> var1);

    void update(BaseIdPrimaryKeyEntity<?> var1);
}
