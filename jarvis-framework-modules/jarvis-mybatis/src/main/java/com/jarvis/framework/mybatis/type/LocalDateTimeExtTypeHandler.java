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

@MappedTypes({LocalDateTime.class})
public class LocalDateTimeExtTypeHandler extends LocalDateTimeTypeHandler {

    public void setParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            try {
                ps.setNull(i, JdbcType.TIMESTAMP.TYPE_CODE);
            } catch (SQLException var7) {
                throw new TypeException("Error setting null for parameter #" + i + " with JdbcType " + jdbcType + " . Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. Cause: " + var7, var7);
            }
        } else {
            try {
                this.setNonNullParameter(ps, i, parameter, jdbcType);
            } catch (Exception var6) {
                throw new TypeException("Error setting non null for parameter #" + i + " with JdbcType " + jdbcType + " . Try setting a different JdbcType for this parameter or a different configuration property. Cause: " + var6, var6);
            }
        }

    }

    public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType) throws SQLException {
        if (DatabaseIdHolder.isOscar()) {
            ps.setTimestamp(i, new Timestamp(parameter.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        } else {
            ps.setObject(i, parameter);
        }

    }

    public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (DatabaseIdHolder.isOscar()) {
            Timestamp timestamp = rs.getTimestamp(columnName);
            return this.toLocalDateTime(timestamp);
        } else {
            return (LocalDateTime)rs.getObject(columnName, LocalDateTime.class);
        }
    }

    public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (DatabaseIdHolder.isOscar()) {
            Timestamp timestamp = rs.getTimestamp(columnIndex);
            return this.toLocalDateTime(timestamp);
        } else {
            return (LocalDateTime)rs.getObject(columnIndex, LocalDateTime.class);
        }
    }

    public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (DatabaseIdHolder.isOscar()) {
            Timestamp timestamp = cs.getTimestamp(columnIndex);
            return this.toLocalDateTime(timestamp);
        } else {
            return cs.getObject(columnIndex, LocalDateTime.class);
        }
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        Instant instant = timestamp.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }
}
