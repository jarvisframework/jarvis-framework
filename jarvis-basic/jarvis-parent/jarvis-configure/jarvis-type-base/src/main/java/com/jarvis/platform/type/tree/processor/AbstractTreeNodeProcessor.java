package com.jarvis.platform.type.tree.processor;

import com.jarvis.framework.core.tree.TreeNode;
import com.jarvis.framework.util.CamelCaseUtil;
import com.jarvis.platform.app.modular.tree.constant.ArchiveTreeConstant;
import com.jarvis.platform.app.modular.tree.dto.ArchiveTreeNodeDTO;
import com.jarvis.platform.app.modular.tree.entity.ArchiveTree;
import com.jarvis.platform.type.tree.TreeNodeProcessor;

import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年11月1日
 */
public abstract class AbstractTreeNodeProcessor implements TreeNodeProcessor {

    /**
     * @see com.gdda.archives.platform.type.tree.TreeNodeProcessor#process(com.gdda.archives.platform.app.modular.tree.entity.ArchiveTree,
     *      java.lang.Long)
     */
    @Override
    public List<TreeNode> process(ArchiveTree tree, String parentId, Long menuId) {
        if (ArchiveTreeConstant.VALUE_TYPE_DYNAMIC == tree.getValueType()) {
            return dynamicNodes(tree, parentId, menuId);
        }
        return staticNodes(tree, menuId);
    }

    /**
     * 静态节点转树节点
     *
     * @param tree 树配置
     * @return List<TreeNode>
     */
    protected List<TreeNode> staticNodes(ArchiveTree tree, Long menuId) {
        return Lists.newArrayList(staticNode(tree));
    }

    protected TreeNode staticNode(ArchiveTree tree) {
        final ArchiveTreeNodeDTO nodeData = toArchiveTreeNodeDTO(tree, String.valueOf(tree.getParentId()));
        processArchiveTreeNodeDTO(nodeData, tree);
        return TreeNode.create().id(String.valueOf(tree.getId())).name(tree.getName())
            .isLeaf(ArchiveTreeConstant.NOCHILD == tree.getChild()).data(nodeData);
    }

    /**
     * 处理树节点数据
     *
     * @param nodeData 节点数据
     * @param tree 树节点配置
     */
    protected void processArchiveTreeNodeDTO(ArchiveTreeNodeDTO nodeData, ArchiveTree tree) {
    }

    /**
     * 动态节点转树节点
     *
     * @param tree 树配置
     * @return List<TreeNode>
     */
    abstract protected List<TreeNode> dynamicNodes(ArchiveTree tree, String parentId, Long menuId);

    /**
     * 过滤条件（字段）
     *
     * @param columnName 字段名称
     * @param value 值
     * @return 条件
     */
    protected String toColumnFilter(String columnName, Object value) {
        if (null == value) {
            return "Q_NULL_" + CamelCaseUtil.toLowerCamelCase(columnName) + "=1";
        }
        return "Q_EQ_" + CamelCaseUtil.toLowerCamelCase(columnName) + "=" + value;
    }

    /**
     * 过滤条件（标签）
     *
     * @param columnLabel 字段标签
     * @param value 值
     * @return 条件
     */
    protected String toLabelFilter(String columnLabel, Object value) {
        if (null == value) {
            return "Q_NULL_" + CamelCaseUtil.toUpperCamelCase(columnLabel) + "=1";
        }
        return "Q_EQ_" + CamelCaseUtil.toUpperCamelCase(columnLabel) + "=" + value;
    }

    protected ArchiveTreeNodeDTO toArchiveTreeNodeDTO(ArchiveTree tree, String parentId) {
        final ArchiveTreeNodeDTO data = new ArchiveTreeNodeDTO();

        data.setParentId(parentId);
        data.setCounted(tree.getCounted());
        data.setFilingCode(tree.getFilingCode());
        data.setLayerCode(tree.getLayerCode());
        data.setTableId(tree.getTableId());
        data.setType(tree.getType());
        data.setValue(tree.getValue());

        return data;
    }

}
