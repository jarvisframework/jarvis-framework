package com.jarvis.framework.database.upgrade.convert.support;

import com.jarvis.framework.constant.DatabaseIdEnum;
import com.jarvis.framework.database.upgrade.convert.FunctionConverter;
import com.jarvis.framework.database.upgrade.convert.parser.GenericTokenParser;
import com.jarvis.framework.database.upgrade.convert.parser.TokenHandler;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月8日
 */
public class DateFunctionConverter implements FunctionConverter {

    private final String openSymbol = "$date{";

    private final String closeSymbol = "}";

    /**
     *
     * @see com.jarvis.framework.database.upgrade.convert.FunctionConverter#convert(java.lang.String, java.lang.String)
     */
    @Override
    public String convert(String sql, String databaseId) {
        final GenericTokenParser parser = new GenericTokenParser(openSymbol, closeSymbol,
                new DateTokenHandler(databaseId));
        return parser.parse(sql);
    }

    private class DateTokenHandler implements TokenHandler {

        private final String databaseId;

        public DateTokenHandler(String databaseId) {
            this.databaseId = databaseId;
        }

        /**
         *
         * @see com.jarvis.framework.database.upgrade.convert.parser.TokenHandler#handle(java.lang.String)
         */
        @Override
        public String handle(String token) {
            token = token.replaceAll("'", "");
            if (DatabaseIdEnum.Oracle.getCode().equals(databaseId)) {
                return String.format("to_date('%s', 'yyyy-mm-dd hh24:mi:ss')", token);
            }
            return String.format("'%s'", token);
        }

    }

    public static void main(String[] args) {
        final String sql = "insert into t_user(id, name, created_time, updated_time)values(1, 'superadmin', $date{2010-01-09}, $date{2010-01-09})";

        System.out.println(new DateFunctionConverter().convert(sql, "mysql"));
    }

}
