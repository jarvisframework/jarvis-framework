package com.jarvis.framework.mybatis.type;

import com.jarvis.framework.mybatis.mapping.DatabaseIdHolder;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.LocalDateTypeHandler;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeException;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月25日
 */
@MappedTypes(LocalDate.class)
public class LocalDateExtTypeHandler extends LocalDateTypeHandler {

    @Override
    public void setParameter(PreparedStatement ps, int i, LocalDate parameter, JdbcType jdbcType)
            throws SQLException {
        if (parameter == null) {
            try {
                ps.setNull(i, JdbcType.DATE.TYPE_CODE);
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

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDate parameter, JdbcType jdbcType)
            throws SQLException {
        if (DatabaseIdHolder.isOscar()) {
            ps.setDate(i, new Date(parameter.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        } else {
            ps.setObject(i, parameter);
        }
    }

    @Override
    public LocalDate getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (DatabaseIdHolder.isOscar()) {
            final Date date = rs.getDate(columnName);
            return toLocalDate(date);
        }
        return rs.getObject(columnName, LocalDate.class);
    }

    @Override
    public LocalDate getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (DatabaseIdHolder.isOscar()) {
            final Date date = rs.getDate(columnIndex);
            return toLocalDate(date);
        }
        return rs.getObject(columnIndex, LocalDate.class);
    }

    @Override
    public LocalDate getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (DatabaseIdHolder.isOscar()) {
            final Date date = cs.getDate(columnIndex);
            return toLocalDate(date);
        }
        return cs.getObject(columnIndex, LocalDate.class);
    }

    private LocalDate toLocalDate(Date date) {
        final Instant instant = date.toInstant();
        final ZoneId zone = ZoneId.systemDefault();
        final LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalDate();
    }

}
