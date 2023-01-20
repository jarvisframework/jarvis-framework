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

@MappedTypes({LocalDate.class})
public class LocalDateExtTypeHandler extends LocalDateTypeHandler {

    public void setParameter(PreparedStatement ps, int i, LocalDate parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            try {
                ps.setNull(i, JdbcType.DATE.TYPE_CODE);
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

    public void setNonNullParameter(PreparedStatement ps, int i, LocalDate parameter, JdbcType jdbcType) throws SQLException {
        if (DatabaseIdHolder.isOscar()) {
            ps.setDate(i, new Date(parameter.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        } else {
            ps.setObject(i, parameter);
        }

    }

    public LocalDate getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (DatabaseIdHolder.isOscar()) {
            Date date = rs.getDate(columnName);
            return this.toLocalDate(date);
        } else {
            return (LocalDate)rs.getObject(columnName, LocalDate.class);
        }
    }

    public LocalDate getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (DatabaseIdHolder.isOscar()) {
            Date date = rs.getDate(columnIndex);
            return this.toLocalDate(date);
        } else {
            return (LocalDate)rs.getObject(columnIndex, LocalDate.class);
        }
    }

    public LocalDate getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (DatabaseIdHolder.isOscar()) {
            Date date = cs.getDate(columnIndex);
            return this.toLocalDate(date);
        } else {
            return (LocalDate)cs.getObject(columnIndex, LocalDate.class);
        }
    }

    private LocalDate toLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalDate();
    }
}
