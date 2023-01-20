package com.jarvis.framework.database.upgrade.util;

import com.jarvis.framework.database.upgrade.convert.DelegateTableConverter;
import com.jarvis.framework.database.upgrade.convert.TableConverter;
import com.jarvis.framework.database.upgrade.dialect.UpgradeDialect;
import com.jarvis.framework.database.upgrade.model.AddColumn;
import com.jarvis.framework.database.upgrade.model.DatabaseUpgrade;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UpgradeDialectUtil {

    public static List<String> toDdlSql(UpgradeDialect dialect, DatabaseUpgrade upgrade) {
        List<String> sqls = new ArrayList();
        TableConverter converter = new DelegateTableConverter();
        Optional.ofNullable(upgrade.getCreateTables()).ifPresent((models) -> {
            models.forEach((model) -> {
                sqls.addAll(dialect.getCreateTableSql(model));
            });
        });
        Optional.ofNullable(upgrade.getRenameTables()).ifPresent((models) -> {
            models.forEach((model) -> {
                sqls.addAll(dialect.getRenameTableSql(model));
            });
        });
        Optional.ofNullable(upgrade.getDropTables()).ifPresent((models) -> {
            models.forEach((model) -> {
                sqls.addAll(dialect.getDropTableSql(model));
            });
        });
        Optional.ofNullable(upgrade.getAddColumns()).ifPresent((models) -> {
            models.forEach((model) -> {
                AddColumn targetAddColumn = new AddColumn();
                BeanUtils.copyProperties(model, targetAddColumn);
                converter.convert(model.getTableName()).forEach((tableName) -> {
                    targetAddColumn.setTableName(tableName);
                    sqls.addAll(dialect.getAddColumnSql(targetAddColumn));
                });
            });
        });
        Optional.ofNullable(upgrade.getRenameColumns()).ifPresent((models) -> {
            models.forEach((model) -> {
                sqls.addAll(dialect.getRenameColumnSql(model));
            });
        });
        Optional.ofNullable(upgrade.getModifyColumns()).ifPresent((models) -> {
            models.forEach((model) -> {
                sqls.addAll(dialect.getModifyColumnSql(model));
            });
        });
        Optional.ofNullable(upgrade.getDropColumns()).ifPresent((models) -> {
            models.forEach((model) -> {
                sqls.addAll(dialect.getDropColumnSql(model));
            });
        });
        Optional.ofNullable(upgrade.getCreateIndexes()).ifPresent((models) -> {
            models.forEach((model) -> {
                sqls.addAll(dialect.getCreateIndexSql(model));
            });
        });
        Optional.ofNullable(upgrade.getDropIndexes()).ifPresent((models) -> {
            models.forEach((model) -> {
                sqls.addAll(dialect.getDropIndexSql(model));
            });
        });
        return sqls;
    }
}
