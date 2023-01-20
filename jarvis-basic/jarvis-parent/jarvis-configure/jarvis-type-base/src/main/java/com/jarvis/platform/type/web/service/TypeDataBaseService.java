package com.jarvis.platform.type.web.service;

import com.jarvis.framework.search.DynamicEntityQuery;
import com.jarvis.framework.search.Page;
import org.apache.ibatis.javassist.bytecode.stackmap.TypeData;

import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年10月31日
 */
public interface TypeDataBaseService {

    /**
     * 新增
     *
     * @param tableId 表id
     * @param entity 数据
     * @param menuId 菜单id
     * @return TypeData
     */
    TypeData insert(Long tableId, TypeData entity, Long menuId);

    /**
     * 更新
     *
     * @param tableId 表id
     * @param entity 数据
     * @param menuId 菜单id
     * @return TypeData
     */
    TypeData update(Long tableId, TypeData entity, Long menuId);

    /**
     * 逻辑删除
     *
     * @param tableId 表id
     * @param ids 数据ids
     * @param menuId 菜单id
     * @return TypeData
     */
    Boolean updateDeleted(Long tableId, List<Long> ids, Long menuId);

    /**
     * 分页
     *
     * @param tableId 表id
     * @param page 分页
     * @param criterion 条件
     * @param keyword 关键字
     * @param menuId 菜单id
     * @return List
     */
    List<TypeData> page(Long tableId, Page page, DynamicEntityQuery criterion, String keyword, Long menuId);

    /**
     * 根节点
     *
     * @param menuId 菜单id
     * @return List<TreeNode>
     */
    List<TreeNode> root(Long menuId);

    /**
     * 树节点
     *
     * @param parentId 父节点
     * @param menuId 菜单id
     * @return List<TreeNode>
     */
    List<TreeNode> tree(String parentId, Long menuId);

    /**
     * 详情
     *
     * @param tableId 表id
     * @param id id
     * @param menuId 菜单id
     * @return TypeData
     */
    TypeData getById(Long tableId, Long id, Long menuId);

}
