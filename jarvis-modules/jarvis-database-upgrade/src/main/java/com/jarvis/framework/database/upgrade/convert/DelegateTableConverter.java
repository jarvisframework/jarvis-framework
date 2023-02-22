package com.jarvis.framework.database.upgrade.convert;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月8日
 */
public class DelegateTableConverter implements TableConverter {

    private static TableConverter converter;

    /**
     *
     * @see com.jarvis.framework.database.upgrade.convert.TableConverter#convert(java.lang.String)
     */
    @Override
    public List<String> convert(String tableName) {
        if (null != converter) {
            return convert(tableName);
        }
        return Collections.singletonList(tableName);
    }

    public static void registConverter(TableConverter converter) {
        DelegateTableConverter.converter = converter;
    }

}
