package com.jarvis.framework.database.upgrade.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月1日
 */
public class DatabaseUpgrade {

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 创建表结构
     */
    private List<CreateTable> createTables = new ArrayList<>();

    /**
     * 重命名表名
     */
    private List<RenameTable> renameTables = new ArrayList<>();

    /**
     * 删除表结构
     */
    private List<DropTable> dropTables = new ArrayList<>();

    /**
     * 添加表字段
     */
    private List<AddColumn> addColumns = new ArrayList<>();

    /**
     * 修改表字段
     */
    private List<ModifyColumn> modifyColumns = new ArrayList<>();

    /**
     * 修改字段名称
     */
    private List<RenameColumn> renameColumns = new ArrayList<>();

    /**
     * 删除字段
     */
    private List<DropColumn> dropColumns = new ArrayList<>();

    /**
     * 创建索引
     */
    private List<CreateIndex> createIndexes = new ArrayList<>();

    /**
     * 删除索引
     */
    private List<DropIndex> dropIndexes = new ArrayList<>();

    /**
     * 通用SQL语句，如insert/update/delete
     *
     * <pre>
     * 现支持日期格式自动转换对应数据库格式
     * 写法：$date{2021-04-08}/$date{2021-04-08 17:52:23}，示例如下：
     * insert into t_user(id, birth_date)values(1, $date{2021-04-08})
     * </pre>
     *
     * 注意：SQL不能有数据库特有函数或语法（除支持以外）
     */
    private List<String> sqls = new ArrayList<>();

    /**
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * @return the createTables
     */
    public List<CreateTable> getCreateTables() {
        return createTables;
    }

    /**
     * @param createTables the createTables to set
     */
    public void setCreateTables(List<CreateTable> createTables) {
        this.createTables = createTables;
    }

    /**
     * @return the renameTables
     */
    public List<RenameTable> getRenameTables() {
        return renameTables;
    }

    /**
     * @param renameTables the renameTables to set
     */
    public void setRenameTables(List<RenameTable> renameTables) {
        this.renameTables = renameTables;
    }

    /**
     * @return the dropTables
     */
    public List<DropTable> getDropTables() {
        return dropTables;
    }

    /**
     * @param dropTables the dropTables to set
     */
    public void setDropTables(List<DropTable> dropTables) {
        this.dropTables = dropTables;
    }

    /**
     * @return the addColumns
     */
    public List<AddColumn> getAddColumns() {
        return addColumns;
    }

    /**
     * @param addColumns the addColumns to set
     */
    public void setAddColumns(List<AddColumn> addColumns) {
        this.addColumns = addColumns;
    }

    /**
     * @return the modifyColumns
     */
    public List<ModifyColumn> getModifyColumns() {
        return modifyColumns;
    }

    /**
     * @param modifyColumns the modifyColumns to set
     */
    public void setModifyColumns(List<ModifyColumn> modifyColumns) {
        this.modifyColumns = modifyColumns;
    }

    /**
     * @return the renameColumns
     */
    public List<RenameColumn> getRenameColumns() {
        return renameColumns;
    }

    /**
     * @param renameColumns the renameColumns to set
     */
    public void setRenameColumns(List<RenameColumn> renameColumns) {
        this.renameColumns = renameColumns;
    }

    /**
     * @return the dropColumns
     */
    public List<DropColumn> getDropColumns() {
        return dropColumns;
    }

    /**
     * @param dropColumns the dropColumns to set
     */
    public void setDropColumns(List<DropColumn> dropColumns) {
        this.dropColumns = dropColumns;
    }

    /**
     * @return the createIndexs
     */
    public List<CreateIndex> getCreateIndexes() {
        return createIndexes;
    }

    /**
     * @param createIndexes the createIndexes to set
     */
    public void setCreateIndexs(List<CreateIndex> createIndexes) {
        this.createIndexes = createIndexes;
    }

    /**
     * @return the dropIndexes
     */
    public List<DropIndex> getDropIndexes() {
        return dropIndexes;
    }

    /**
     * @param dropIndexes the dropIndexes to set
     */
    public void setDropIndexes(List<DropIndex> dropIndexes) {
        this.dropIndexes = dropIndexes;
    }

    /**
     * @return the dmlSqls
     */
    public List<String> getSqls() {
        return sqls;
    }

    /**
     * @param sqls the sqls to set
     */
    public void setSqls(List<String> sqls) {
        this.sqls = sqls;
    }

}
