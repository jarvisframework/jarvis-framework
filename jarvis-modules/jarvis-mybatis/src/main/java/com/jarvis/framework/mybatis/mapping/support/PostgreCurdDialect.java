package com.jarvis.framework.mybatis.mapping.support;

import com.jarvis.framework.mybatis.mapping.CurdDialect;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月22日
 */
public class PostgreCurdDialect extends MysqlCurdDialect {

    public static final CurdDialect INSTANCE = new PostgreCurdDialect();

}
