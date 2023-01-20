package com.jarvis.platform.type.tree;

import com.jarvis.framework.constant.CommonConstant;
import com.jarvis.framework.core.tree.TreeNode;
import com.jarvis.platform.app.modular.tree.dto.ArchiveTreeNodeDTO;
import com.jarvis.platform.app.modular.tree.entity.ArchiveTree;
import com.jarvis.platform.app.sdk.ArchiveTreeSdkService;
import com.jarvis.platform.type.authority.TypeAuthorityProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年11月1日
 */
@Component
public class ArchiveTreeNodeManager {

    @Autowired
    private List<TreeNodeProcessor> processors;

    @Autowired
    private TypeAuthorityProcessor authorityProcessor;

    @Autowired
    private ArchiveTreeSdkService treeSdkService;

    /**
     * 获取根节点集合
     *
     * @param menuId 菜单ID
     * @return List<TreeNode>
     */
    public List<TreeNode> queryRootNodesByMenuId(Long menuId) {
        final List<ArchiveTree> trees = treeSdkService.queryTrees(menuId);
        return trees.stream().map(e -> toTreeRootNode(e)).collect(Collectors.toList());
    }

    private TreeNode toTreeRootNode(ArchiveTree tree) {
        final ArchiveTreeNodeDTO nodeData = new ArchiveTreeNodeDTO();
        BeanUtils.copyProperties(tree, nodeData);
        return TreeNode.create()
            .id(String.valueOf(tree.getId()))
            .name(tree.getName())
            .hidden(CommonConstant.NO == tree.getRooted())
            .isLeaf(false).data(nodeData);
    }

    /**
     * 把ArchiveTree转成TreeNode
     *
     * @param parentId 父节点ID
     * @param menuId 菜单ID
     * @return List<TreeNode>
     */
    public List<TreeNode> queryTreeNodesByParentId(String parentId, Long menuId) {
        final List<TreeNode> data = Lists.newArrayList();

        final String[] ids = parentId.split(".");
        final Long id = Long.parseLong(ids[0]);
        final List<ArchiveTree> trees = treeSdkService.queryByParentId(id);

        for (final ArchiveTree tree : trees) {
            data.addAll(process(tree, parentId, menuId));
        }
        authorityProcessor.processTreeNode(menuId, data);
        return data;
    }

    private List<TreeNode> process(ArchiveTree tree, String parentId, Long menuId) {
        for (final TreeNodeProcessor p : processors) {
            if (p.support(tree)) {
                return p.process(tree, parentId, menuId);
            }
        }
        return Lists.newArrayList();
    }

}
