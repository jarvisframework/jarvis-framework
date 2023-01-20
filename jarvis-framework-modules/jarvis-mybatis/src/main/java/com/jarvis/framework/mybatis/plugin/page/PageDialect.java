package com.jarvis.framework.mybatis.plugin.page;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.RowBounds;

public interface PageDialect {

    boolean skip(MappedStatement var1, Object var2, RowBounds var3);

    Object processPageParameter(MappedStatement var1, Object var2, BoundSql var3, CacheKey var4);

    String getPageSql(MappedStatement var1, BoundSql var2, Object var3, RowBounds var4, CacheKey var5);
}
