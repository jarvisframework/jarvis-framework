package com.jarvis.framework.database.upgrade.dialect;

import com.jarvis.framework.database.upgrade.model.AddColumn;
import com.jarvis.framework.database.upgrade.model.CreateIndex;
import com.jarvis.framework.database.upgrade.model.CreateTable;
import com.jarvis.framework.database.upgrade.model.DropColumn;
import com.jarvis.framework.database.upgrade.model.DropIndex;
import com.jarvis.framework.database.upgrade.model.DropTable;
import com.jarvis.framework.database.upgrade.model.ModifyColumn;
import com.jarvis.framework.database.upgrade.model.RenameColumn;
import com.jarvis.framework.database.upgrade.model.RenameTable;

import java.util.Collection;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月1日
 */
public interface UpgradeDialect {

    /**
     * 创建表SQL
     *
     * @param model
     * @return
     */
    Collection<String> getCreateTableSql(CreateTable model);

    /**
     * 删除表SQL
     *
     * @param model
     * @return
     */
    Collection<String> getDropTableSql(DropTable model);

    /**
     * 重命名表名SQL
     *
     * @param model
     * @return
     */
    Collection<String> getRenameTableSql(RenameTable model);

    /**
     * 添加字段SQL
     *
     * @param model
     * @return
     */
    Collection<String> getAddColumnSql(AddColumn model);

    /**
     * 修改字段SQL
     *
     * @param model
     * @return
     */
    Collection<String> getModifyColumnSql(ModifyColumn model);

    /**
     * 重命名字段SQL
     *
     * @param model
     * @return
     */
    Collection<String> getRenameColumnSql(RenameColumn model);

    /**
     * 删除字段SQL
     *
     * @param model
     * @return
     */
    Collection<String> getDropColumnSql(DropColumn model);

    /**
     * 创建索引SQL
     *
     * @param model
     * @return
     */
    Collection<String> getCreateIndexSql(CreateIndex model);

    /**
     * 删除索引SQL
     *
     * @param model
     * @return
     */
    Collection<String> getDropIndexSql(DropIndex model);
}
