package com.jarvis.framework.database.upgrade.convert;

import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月8日
 */
public interface TableConverter {

    public List<String> convert(String tableName);
}
