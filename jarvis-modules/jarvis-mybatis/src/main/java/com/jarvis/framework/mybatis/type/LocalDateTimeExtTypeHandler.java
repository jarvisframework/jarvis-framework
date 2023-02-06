package com.jarvis.framework.mybatis.type;

import com.jarvis.framework.mybatis.mapping.DatabaseIdHolder;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeException;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月25日
 */
@MappedTypes(LocalDateTime.class)
public class LocalDateTimeExtTypeHandler extends LocalDateTimeTypeHandler {

    @Override
    public void setParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType)
            throws SQLException {
        if (parameter == null) {
            try {
                ps.setNull(i, JdbcType.TIMESTAMP.TYPE_CODE);
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
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType)
            throws SQLException {
        if (DatabaseIdHolder.isOscar()) {
            ps.setTimestamp(i,
                    new Timestamp(parameter.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        } else {
            ps.setObject(i, parameter);
        }
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (DatabaseIdHolder.isOscar()) {
            final Timestamp timestamp = rs.getTimestamp(columnName);
            return toLocalDateTime(timestamp);
        }
        return rs.getObject(columnName, LocalDateTime.class);
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (DatabaseIdHolder.isOscar()) {
            final Timestamp timestamp = rs.getTimestamp(columnIndex);
            return toLocalDateTime(timestamp);
        }
        return rs.getObject(columnIndex, LocalDateTime.class);
    }

    @Override
    public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (DatabaseIdHolder.isOscar()) {
            final Timestamp timestamp = cs.getTimestamp(columnIndex);
            return toLocalDateTime(timestamp);
        }
        return cs.getObject(columnIndex, LocalDateTime.class);
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        final Instant instant = timestamp.toInstant();
        final ZoneId zone = ZoneId.systemDefault();
        final LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }

}
