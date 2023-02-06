package com.jarvis.framework.mybatis.mapping.support;

import com.jarvis.framework.mybatis.mapping.CurdDialect;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月20日
 */
public class DmCurdDialect extends MysqlCurdDialect {

    public static final CurdDialect INSTANCE = new DmCurdDialect();

}
