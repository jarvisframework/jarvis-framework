package com.jarvis.framework.mybatis.type;

import org.apache.ibatis.type.FloatTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月25日
 */
public class FloatExtTypeHandler extends FloatTypeHandler {

    @Override
    public void setParameter(PreparedStatement ps, int i, Float parameter, JdbcType jdbcType)
            throws SQLException {
        if (parameter == null) {
            try {
                ps.setNull(i, JdbcType.FLOAT.TYPE_CODE);
            } catch (final SQLException e) {
                throw new TypeException("Error setting null for parameter #" + i + " with JdbcType " + jdbcType + " . "
                        + "Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. "
                        + "Cause: " + e, e);
            }
        } else {
            try {
                setNonNullParameter(ps, i, parameter, jdbcType);
            } catch (final Exception e) {
                throw new TypeException(
                        "Error setting non null for parameter #" + i + " with JdbcType " + jdbcType + " . "
                                + "Try setting a different JdbcType for this parameter or a different configuration property. "
                                + "Cause: " + e,
                        e);
            }
        }
    }
}
