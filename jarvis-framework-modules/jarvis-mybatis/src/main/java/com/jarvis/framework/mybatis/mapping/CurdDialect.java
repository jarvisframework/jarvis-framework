package com.jarvis.framework.mybatis.mapping;

public interface CurdDialect {
    String insertAll(Collection<BaseIdPrimaryKeyEntity<?>> var1);

    String updateAll(Collection<BaseIdPrimaryKeyEntity<?>> var1);

    String deleteAll(Collection<BaseIdPrimaryKeyEntity<?>> var1);

    String deleteById(String var1);

    String deleteByIds(String var1);

    String getById(String var1);

    String getBy(CriteriaQuery<?> var1);

    String getBy(MultipleQuery var1);

    String page(Page var1, CriteriaQuery<?> var2);

    String exists(CriteriaQuery<?> var1);
}
