package com.jarvis.platform.type.web.service.impl;

import com.jarvis.framework.core.exception.BusinessException;
import com.jarvis.framework.search.DynamicEntityQuery;
import com.jarvis.framework.search.Page;
import com.jarvis.platform.type.authority.TypeAuthorityProcessor;
import com.jarvis.platform.type.tree.ArchiveTreeNodeManager;
import com.jarvis.platform.type.web.service.TypeDataBaseService;
import org.apache.ibatis.javassist.bytecode.stackmap.TypeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年10月31日
 */
public class TypeDataBaseServiceImpl implements TypeDataBaseService {

    @Autowired
    protected TypeDataRefSdkService refSdkService;

    @Autowired
    protected ArchiveTreeNodeManager treeNodeManager;

    @Autowired
    protected TypeAuthorityProcessor authorityProcessor;

    /**
     *
     * @see com.gdda.archives.platform.type.web.service.TypeDataBaseService#insert(java.lang.Long,
     *      com.gdda.archives.platform.type.modular.archive.entity.TypeData, java.lang.Long)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public TypeData insert(Long tableId, TypeData entity, Long menuId) {
        beforeInsert(tableId, entity, menuId);
        entity = refSdkService.insert(tableId, entity);
        afterInsert(tableId, entity, menuId);
        return entity;
    }

    /**
     * 新增前二次处理
     *
     * @param tableId 表ID
     * @param entity 数据
     * @param menuId 菜单ID
     */
    protected void beforeInsert(Long tableId, TypeData entity, Long menuId) {

    }

    /**
     * 新增后二次处理
     *
     * @param tableId 表ID
     * @param entity 数据
     * @param menuId 菜单ID
     */
    protected void afterInsert(Long tableId, TypeData entity, Long menuId) {

    }

    /**
     *
     * @see com.gdda.archives.platform.type.web.service.TypeDataBaseService#update(java.lang.Long,
     *      com.gdda.archives.platform.type.modular.archive.entity.TypeData, java.lang.Long)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public TypeData update(Long tableId, TypeData entity, Long menuId) {
        beforeUpdate(tableId, entity, menuId);
        entity = refSdkService.update(tableId, entity);
        afterUpdate(tableId, entity, menuId);
        return entity;
    }

    /**
     * 修改前二次处理
     *
     * @param tableId 表ID
     * @param entity 数据
     * @param menuId 菜单ID
     */
    protected void beforeUpdate(Long tableId, TypeData entity, Long menuId) {

    }

    /**
     * 修改后二次处理
     *
     * @param tableId 表ID
     * @param entity 数据
     * @param menuId 菜单ID
     */
    protected void afterUpdate(Long tableId, TypeData entity, Long menuId) {

    }

    /**
     *
     * @see com.gdda.archives.platform.type.web.service.TypeDataBaseService#updateDeleted(java.lang.Long,
     *      java.util.List, java.lang.Long)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateDeleted(Long tableId, List<Long> ids, Long menuId) {
        final UpdateDeletedDTO param = new UpdateDeletedDTO();
        param.setIds(ids);
        beforeUpdateDeleted(tableId, param, menuId);
        final boolean success = refSdkService.updateDeleted(tableId, param);
        afterUpdateDeleted(tableId, param, success, menuId);
        return success;
    }

    /**
     * 逻辑删除前二次处理
     *
     * @param tableId 表ID
     * @param param 条件
     * @param menuId 菜单ID
     */
    protected void beforeUpdateDeleted(Long tableId, UpdateDeletedDTO param, Long menuId) {
        throw BusinessException.create("请指定删除后的存储位置等信息！");
    }

    /**
     * 逻辑删除后二次处理
     *
     * @param tableId 表ID
     * @param param 条件
     * @param menuId 菜单ID
     */
    protected void afterUpdateDeleted(Long tableId, UpdateDeletedDTO param, boolean success, Long menuId) {
    }

    /**
     *
     * @see com.gdda.archives.platform.type.web.service.TypeDataBaseService#page(java.lang.Long,
     *      com.gdda.archives.framework.search.Page, com.gdda.archives.framework.search.DynamicEntityQuery,
     *      java.lang.String, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<TypeData> page(Long tableId, Page page, DynamicEntityQuery criterion, String keyword, Long menuId) {
        authorityProcessor.processDataQuery(tableId, menuId, criterion);
        processPageCritertion(tableId, criterion, keyword, menuId);
        final List<TypeData> data = (List<TypeData>) refSdkService.page(tableId, page, criterion, keyword);
        processPageData(data, tableId, criterion, keyword, menuId);
        if (page.isCounted()) {
            page.setContent(data);
        }
        return data;
    }

    /**
     * 分页条件二次处理
     *
     * @param tableId 表ID
     * @param criterion 条件
     * @param keyword 关键字
     * @param menuId 菜单ID
     */
    protected void processPageCritertion(Long tableId, DynamicEntityQuery criterion, String keyword, Long menuId) {

    }

    /**
     * 分面结果二次处理
     *
     * @param data 结果集
     * @param tableId 表ID
     * @param criterion 条件
     * @param keyword 关键字
     * @param menuId 菜单ID
     * @return List<TypeData>
     */
    protected List<TypeData> processPageData(List<TypeData> data, Long tableId, DynamicEntityQuery criterion,
        String keyword, Long menuId) {

        return data;
    }

    /**
     *
     * @see com.gdda.archives.platform.type.web.service.TypeDataBaseService#root(java.lang.Long)
     */
    @Override
    public List<TreeNode> root(Long menuId) {
        return treeNodeManager.queryRootNodesByMenuId(menuId);
    }

    /**
     *
     * @see com.gdda.archives.platform.type.web.service.TypeDataBaseService#tree(java.lang.Long, java.lang.Long)
     */
    @Override
    public List<TreeNode> tree(String parentId, Long menuId) {
        final List<TreeNode> data = treeNodeManager.queryTreeNodesByParentId(parentId, menuId);
        return pocessTreeNodes(data, menuId);
    }

    /**
     * 树节点二次处理
     *
     * @param data 树节点集合
     * @param menuId 菜单ID
     * @return List
     */
    protected List<TreeNode> pocessTreeNodes(List<TreeNode> data, Long menuId) {
        return data;
    }

    /**
     *
     * @see com.gdda.archives.platform.type.web.service.TypeDataBaseService#getById(java.lang.Long, java.lang.Long,
     *      java.lang.Long)
     */
    @Override
    public TypeData getById(Long tableId, Long id, Long menuId) {
        return refSdkService.getById(tableId, id);
    }

}
