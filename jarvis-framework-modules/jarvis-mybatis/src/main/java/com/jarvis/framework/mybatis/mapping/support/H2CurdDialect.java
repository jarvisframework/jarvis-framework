package com.jarvis.framework.mybatis.mapping.support;

import com.jarvis.framework.mybatis.mapping.CurdDialect;

public class H2CurdDialect extends PostgreCurdDialect {

    public static final CurdDialect INSTANCE = new H2CurdDialect();

}
