package com.jarvis.framework.database.upgrade.dialect.support;

import com.jarvis.framework.database.upgrade.constant.DataTypeEnum;
import com.jarvis.framework.database.upgrade.dialect.UpgradeDialect;
import com.jarvis.framework.database.upgrade.model.Column;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年8月1日
 */
public class DmUpgradeDialect extends OracleUpgradeDialect {

    public static final UpgradeDialect INSTANCE = new DmUpgradeDialect();

    /**
     * @see com.jarvis.framework.database.upgrade.dialect.support.OracleUpgradeDialect#getColumnDataType(
     *      com.jarvis.framework.database.upgrade.model.Column)
     */
    @Override
    protected String getColumnDataType(Column model) {
        final StringBuilder sb = new StringBuilder(64);
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
