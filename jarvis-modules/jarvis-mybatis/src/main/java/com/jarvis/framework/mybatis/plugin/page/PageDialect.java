package com.jarvis.framework.mybatis.plugin.page;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.RowBounds;

/**
 * 数据分页方言
 *
 * @author qiucs
 * @version 1.0.0 2021年3月18日
 */
public interface PageDialect {

    /**
     * 跳过 count 和 分页查询
     *
     * @param ms MappedStatement
     * @param parameter 方法参数
     * @param rowBounds 分页参数
     * @return true 跳过，返回默认查询结果，false 执行分页查询
     */
    boolean skip(MappedStatement ms, Object parameter, RowBounds rowBounds);

    /**
     * 处理查询参数对象
     *
     * @param ms MappedStatement
     * @param parameter
     * @param boundSql
     * @param pageKey
     * @return
     */
    Object processPageParameter(MappedStatement ms, Object parameter, BoundSql boundSql, CacheKey pageKey);

    /**
     * 生成分页查询 sql
     *
     * @param ms MappedStatement
     * @param boundSql 绑定 SQL 对象
     * @param parameter 方法参数
     * @param rowBounds 分页参数
     * @param pageKey 分页缓存 key
     * @return
     */
    String getPageSql(MappedStatement ms, BoundSql boundSql, Object parameter, RowBounds rowBounds,
                      CacheKey pageKey);
}
