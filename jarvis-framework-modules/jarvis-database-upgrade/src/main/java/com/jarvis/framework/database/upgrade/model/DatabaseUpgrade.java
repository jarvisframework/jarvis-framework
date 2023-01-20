package com.jarvis.framework.database.upgrade.model;

import java.util.ArrayList;
import java.util.List;

public class DatabaseUpgrade {
    private Integer version;
    private List<CreateTable> createTables = new ArrayList();
    private List<RenameTable> renameTables = new ArrayList();
    private List<DropTable> dropTables = new ArrayList();
    private List<AddColumn> addColumns = new ArrayList();
    private List<ModifyColumn> modifyColumns = new ArrayList();
    private List<RenameColumn> renameColumns = new ArrayList();
    private List<DropColumn> dropColumns = new ArrayList();
    private List<CreateIndex> createIndexes = new ArrayList();
    private List<DropIndex> dropIndexes = new ArrayList();
    private List<String> sqls = new ArrayList();

    public DatabaseUpgrade() {
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<CreateTable> getCreateTables() {
        return this.createTables;
    }

    public void setCreateTables(List<CreateTable> createTables) {
        this.createTables = createTables;
    }

    public List<RenameTable> getRenameTables() {
        return this.renameTables;
    }

    public void setRenameTables(List<RenameTable> renameTables) {
        this.renameTables = renameTables;
    }

    public List<DropTable> getDropTables() {
        return this.dropTables;
    }

    public void setDropTables(List<DropTable> dropTables) {
        this.dropTables = dropTables;
    }

    public List<AddColumn> getAddColumns() {
        return this.addColumns;
    }

    public void setAddColumns(List<AddColumn> addColumns) {
        this.addColumns = addColumns;
    }

    public List<ModifyColumn> getModifyColumns() {
        return this.modifyColumns;
    }

    public void setModifyColumns(List<ModifyColumn> modifyColumns) {
        this.modifyColumns = modifyColumns;
    }

    public List<RenameColumn> getRenameColumns() {
        return this.renameColumns;
    }

    public void setRenameColumns(List<RenameColumn> renameColumns) {
        this.renameColumns = renameColumns;
    }

    public List<DropColumn> getDropColumns() {
        return this.dropColumns;
    }

    public void setDropColumns(List<DropColumn> dropColumns) {
        this.dropColumns = dropColumns;
    }

    public List<CreateIndex> getCreateIndexes() {
        return this.createIndexes;
    }

    public void setCreateIndexs(List<CreateIndex> createIndexes) {
        this.createIndexes = createIndexes;
    }

    public List<DropIndex> getDropIndexes() {
        return this.dropIndexes;
    }

    public void setDropIndexes(List<DropIndex> dropIndexes) {
        this.dropIndexes = dropIndexes;
    }

    public List<String> getSqls() {
        return this.sqls;
    }

    public void setSqls(List<String> sqls) {
        this.sqls = sqls;
    }
}
