package com.jarvis.framework.database.upgrade.convert;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月8日
 */
public interface FunctionConverter {

    /**
     * SQL转换
     *
     * @param sql
     * @param databaseId
     * @return
     */
    String convert(String sql, String databaseId);
}
