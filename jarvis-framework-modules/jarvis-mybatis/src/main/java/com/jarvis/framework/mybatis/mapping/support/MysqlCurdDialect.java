package com.jarvis.framework.mybatis.mapping.support;

import com.jarvis.framework.mybatis.mapping.AbstractCurdDialect;
import com.jarvis.framework.mybatis.mapping.CurdDialect;

public class MysqlCurdDialect extends AbstractCurdDialect {

    public static final CurdDialect INSTANCE = new MysqlCurdDialect();

}
