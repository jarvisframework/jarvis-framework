package com.jarvis.framework.database.upgrade.convert.support;

import com.jarvis.framework.constant.DatabaseIdEnum;
import com.jarvis.framework.database.upgrade.convert.FunctionConverter;
import com.jarvis.framework.database.upgrade.convert.parser.GenericTokenParser;
import com.jarvis.framework.database.upgrade.convert.parser.TokenHandler;

public class DateFunctionConverter implements FunctionConverter {
    private final String openSymbol = "$date{";
    private final String closeSymbol = "}";

    public DateFunctionConverter() {
    }

    public String convert(String sql, String databaseId) {
        GenericTokenParser parser = new GenericTokenParser("$date{", "}", new DateFunctionConverter.DateTokenHandler(databaseId));
        return parser.parse(sql);
    }

    public static void main(String[] args) {
        String sql = "insert into t_user(id, name, created_time, updated_time)values(1, 'superadmin', $date{2010-01-09}, $date{2010-01-09})";
        System.out.println((new DateFunctionConverter()).convert("insert into t_user(id, name, created_time, updated_time)values(1, 'superadmin', $date{2010-01-09}, $date{2010-01-09})", "mysql"));
    }

    private class DateTokenHandler implements TokenHandler {
        private final String databaseId;

        public DateTokenHandler(String databaseId) {
            this.databaseId = databaseId;
        }

        public String handle(String token) {
            token = token.replaceAll("'", "");
            return DatabaseIdEnum.Oracle.getCode().equals(this.databaseId) ? String.format("to_date('%s', 'yyyy-mm-dd hh24:mi:ss')", token) : String.format("'%s'", token);
        }
    }
}
