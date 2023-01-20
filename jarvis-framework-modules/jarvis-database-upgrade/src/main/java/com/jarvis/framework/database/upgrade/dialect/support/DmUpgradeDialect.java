package com.jarvis.framework.database.upgrade.dialect.support;

import com.jarvis.framework.database.upgrade.constant.DataTypeEnum;
import com.jarvis.framework.database.upgrade.dialect.UpgradeDialect;
import com.jarvis.framework.database.upgrade.model.Column;

public class DmUpgradeDialect extends OracleUpgradeDialect {

    public static final UpgradeDialect INSTANCE = new DmUpgradeDialect();

    protected String getColumnDataType(Column model) {
        StringBuilder sb = new StringBuilder(64);
        if (DataTypeEnum.VARCHAR == model.getDataType()) {
            sb.append(" VARCHAR2(").append(model.getLength()).append(")");
        } else if (DataTypeEnum.TEXT == model.getDataType()) {
            sb.append(" CLOB ");
        } else if (DataTypeEnum.DATE == model.getDataType()) {
            sb.append(" DATE ");
        } else if (DataTypeEnum.DATETIME == model.getDataType()) {
            sb.append(" DATETIME ");
        } else if (DataTypeEnum.INT == model.getDataType()) {
            sb.append(" INT ");
        } else if (DataTypeEnum.BIGINT == model.getDataType()) {
            sb.append(" BIGINT ");
        } else if (DataTypeEnum.DECIMAL == model.getDataType()) {
            sb.append(" NUMBER(").append(model.getLength()).append(",").append(model.getScale()).append(")");
        }

        return sb.toString();
    }
}
