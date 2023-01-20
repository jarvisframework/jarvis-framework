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

public interface UpgradeDialect {

    Collection<String> getCreateTableSql(CreateTable var1);

    Collection<String> getDropTableSql(DropTable var1);

    Collection<String> getRenameTableSql(RenameTable var1);

    Collection<String> getAddColumnSql(AddColumn var1);

    Collection<String> getModifyColumnSql(ModifyColumn var1);

    Collection<String> getRenameColumnSql(RenameColumn var1);

    Collection<String> getDropColumnSql(DropColumn var1);

    Collection<String> getCreateIndexSql(CreateIndex var1);

    Collection<String> getDropIndexSql(DropIndex var1);
}
