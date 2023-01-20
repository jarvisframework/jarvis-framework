package com.jarvis.framework.mybatis.mapping.support;

import com.jarvis.framework.mybatis.mapping.CurdDialect;

public class PostgreCurdDialect extends MysqlCurdDialect {

    public static final CurdDialect INSTANCE = new PostgreCurdDialect();

}
