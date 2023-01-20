package com.jarvis.framework.database.upgrade.convert;

import java.util.Collections;
import java.util.List;

public class DelegateTableConverter implements TableConverter {
    private static TableConverter converter;

    public List<String> convert(String tableName) {
        return null != converter ? this.convert(tableName) : Collections.singletonList(tableName);
    }

    public static void registConverter(TableConverter converter) {
        DelegateTableConverter.converter = converter;
    }
}
