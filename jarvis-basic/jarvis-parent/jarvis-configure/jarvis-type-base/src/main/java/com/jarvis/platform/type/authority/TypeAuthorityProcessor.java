package com.jarvis.platform.type.authority;

import com.jarvis.framework.core.tree.TreeNode;
import com.jarvis.framework.search.DynamicEntityQuery;

import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年10月31日
 */
public interface TypeAuthorityProcessor {

    /**
     * 查询数据权限
     *
     * @param tableId 表id
     * @param menuId 菜单id
     * @param query 条件
     */
    void processDataQuery(Long tableId, Long menuId, DynamicEntityQuery query);

    /**
     * 树节点权限
     *
     * @param menuId 菜单id
     * @param nodes 树节点
     */
    void processTreeNode(Long menuId, List<TreeNode> nodes);

}
