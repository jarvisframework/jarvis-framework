package com.jarvis.framework.mybatis.type;

import org.apache.ibatis.type.BooleanTypeHandler;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月25日
 */
public class BooleanExtTypeHandler extends BooleanTypeHandler {

    @Override
    public Boolean getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        final Object result = rs.getObject(columnName);
        return toBoolean(result);
    }

    private Boolean toBoolean(Object result) {
        if (null == result) {
            return null;
        }
        if (result instanceof Boolean) {
            return (Boolean) result;
        }
        if (result instanceof Number) {
            return ((Number) result).intValue() > 0;
        }

        return "true".equalsIgnoreCase(String.valueOf(result));
    }

    @Override
    public Boolean getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        final Object result = rs.getBoolean(columnIndex);
        return toBoolean(result);
    }

    @Override
    public Boolean getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        final Object result = cs.getBoolean(columnIndex);
        return toBoolean(result);
    }
}
