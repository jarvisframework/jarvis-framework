package com.jarvis.framework.database.upgrade.dialect;

import com.jarvis.framework.database.upgrade.constant.IndexTypeEnum;
import com.jarvis.framework.database.upgrade.model.Column;
import com.jarvis.framework.database.upgrade.model.CreateIndex;
import com.jarvis.framework.database.upgrade.model.DropColumn;
import com.jarvis.framework.database.upgrade.model.DropIndex;
import com.jarvis.framework.database.upgrade.model.DropTable;
import com.jarvis.framework.database.upgrade.model.RenameTable;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月1日
 */
public abstract class AbstractUpgradeDialect implements UpgradeDialect {

    /**
     * 获取字段类型SQL
     *
     * @param model
     * @return
     */
    protected abstract String getColumnDataType(Column model);

    /**
     *
     * @see com.jarvis.framework.database.upgrade.dialect.UpgradeDialect#getDropTableSql(
     *      com.jarvis.framework.database.upgrade.model.DropTable)
     */
    @Override
    public Collection<String> getDropTableSql(DropTable model) {
        return Collections.singletonList("DROP TABLE " + model.getTableName());
    }

    /**
     *
     * @see com.jarvis.framework.database.upgrade.dialect.UpgradeDialect#getRenameTableSql(
     *      com.jarvis.framework.database.upgrade.model.RenameTable)
     */
    @Override
    public Collection<String> getRenameTableSql(RenameTable model) {
        final String sql = ("ALTER TABLE " + model.getTableName() + " RENAME TO " + model.getNewTableName());
        return Collections.singletonList(sql);
    }

    /**
     *
     * @see com.jarvis.framework.database.upgrade.dialect.UpgradeDialect#getDropColumnSql(
     *      com.jarvis.framework.database.upgrade.model.DropColumn)
     */
    @Override
    public Collection<String> getDropColumnSql(DropColumn model) {
        final StringBuilder sb = new StringBuilder(64);

        sb.append("ALTER TABLE ").append(model.getTableName()).append(" DROP COLUMN ")
                .append(StringUtils.collectionToCommaDelimitedString(model.getColumnNames()));

        return Collections.singletonList(sb.toString());
    }

    /**
     *
     * @see com.jarvis.framework.database.upgrade.dialect.UpgradeDialect#getCreateIndexSql(
     *      com.jarvis.framework.database.upgrade.model.CreateIndex)
     */
    @Override
    public Collection<String> getCreateIndexSql(CreateIndex model) {
        final StringBuilder sb = new StringBuilder(64);

        sb.append("CREATE ");
        if (IndexTypeEnum.UNIQUE == model.getIndexType()) {
            sb.append("UNIQUE ");
        }
        sb.append("INDEX ").append(model.getIndexName()).append(" ON ").append(model.getTableName()).append(" (")
                .append(StringUtils.collectionToCommaDelimitedString(model.getColumnNames())).append(")");

        return Collections.singletonList(sb.toString());
    }

    /**
     *
     * @see com.jarvis.framework.database.upgrade.dialect.UpgradeDialect#getDropIndexSql(
     *      com.jarvis.framework.database.upgrade.model.DropIndex)
     */
    @Override
    public Collection<String> getDropIndexSql(DropIndex model) {
        return Collections.singletonList("DROP INDEX " + model.getIndexName() + " ON " + model.getTableName());
    }

}
