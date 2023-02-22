package com.jarvis.framework.mybatis.mapping.support;

import com.jarvis.framework.mybatis.mapping.CurdDialect;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年8月2日
 */
public class H2CurdDialect extends PostgreCurdDialect {

    public static final CurdDialect INSTANCE = new H2CurdDialect();

}
