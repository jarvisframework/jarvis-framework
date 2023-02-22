package com.jarvis.framework.mybatis.plugin.page.support;

import com.jarvis.framework.mybatis.plugin.page.AbstractPageDialect;
import com.jarvis.framework.mybatis.plugin.page.PageDialect;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年3月19日
 */
public class MysqlPageDialect extends AbstractPageDialect {

    public static final PageDialect INSTANCE = new MysqlPageDialect();

}
