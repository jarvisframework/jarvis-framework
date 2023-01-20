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

public abstract class AbstractUpgradeDialect implements UpgradeDialect {
    public AbstractUpgradeDialect() {
    }

    protected abstract String getColumnDataType(Column var1);

    public Collection<String> getDropTableSql(DropTable model) {
        return Collections.singletonList("DROP TABLE " + model.getTableName());
    }

    public Collection<String> getRenameTableSql(RenameTable model) {
        String sql = "ALTER TABLE " + model.getTableName() + " RENAME TO " + model.getNewTableName();
        return Collections.singletonList(sql);
    }

    public Collection<String> getDropColumnSql(DropColumn model) {
        StringBuilder sb = new StringBuilder(64);
        sb.append("ALTER TABLE ").append(model.getTableName()).append(" DROP COLUMN ").append(StringUtils.collectionToCommaDelimitedString(model.getColumnNames()));
        return Collections.singletonList(sb.toString());
    }

    public Collection<String> getCreateIndexSql(CreateIndex model) {
        StringBuilder sb = new StringBuilder(64);
        sb.append("CREATE ");
        if (IndexTypeEnum.UNIQUE == model.getIndexType()) {
            sb.append("UNIQUE ");
        }

        sb.append("INDEX ").append(model.getIndexName()).append(" ON ").append(model.getTableName()).append(" (").append(StringUtils.collectionToCommaDelimitedString(model.getColumnNames())).append(")");
        return Collections.singletonList(sb.toString());
    }

    public Collection<String> getDropIndexSql(DropIndex model) {
        return Collections.singletonList("DROP INDEX " + model.getIndexName() + " ON " + model.getTableName());
    }
}
