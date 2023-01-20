package com.jarvis.platform.type.tree;

import com.jarvis.framework.core.tree.TreeNode;
import com.jarvis.platform.app.modular.tree.entity.ArchiveTree;

import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年10月31日
 */
public interface TreeNodeProcessor {

    /**
     * 是否支持
     */
    boolean support(ArchiveTree tree);

    /**
     * 生成树节点
     *
     * @param tree 树配置
     * @param parentId 父id
     * @param menuId 菜单id
     * @return List<TreeNode>
     */
    List<TreeNode> process(ArchiveTree tree, String parentId, Long menuId);

}
