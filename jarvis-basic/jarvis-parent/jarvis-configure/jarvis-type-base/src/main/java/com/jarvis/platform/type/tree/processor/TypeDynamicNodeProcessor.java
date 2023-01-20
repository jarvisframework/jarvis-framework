package com.jarvis.platform.type.tree.processor;

import com.jarvis.framework.constant.SymbolConstant;
import com.jarvis.framework.core.tree.TreeNode;
import com.jarvis.platform.app.modular.tree.constant.ArchiveTreeConstant;
import com.jarvis.platform.app.modular.tree.dto.ArchiveTreeNodeDTO;
import com.jarvis.platform.app.modular.tree.entity.ArchiveTree;
import com.jarvis.platform.app.modular.type.entity.ArchiveType;
import com.jarvis.platform.app.sdk.ArchiveTypeSdkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年10月31日
 */
@Component
public class TypeDynamicNodeProcessor extends AbstractTreeNodeProcessor implements Ordered {

    @Autowired
    private ArchiveTypeSdkService archiveTypeSdkService;

    /**
     *
     * @see com.gdda.archives.platform.type.tree.TreeNodeProcessor#support(com.gdda.archives.platform.app.modular.tree.entity.ArchiveTree)
     */
    @Override
    public boolean support(ArchiveTree tree) {
        if (null == tree.getValueType()) {
            return false;
        }
        return ArchiveTreeConstant.TYPE_ARCHIVE_TYPE == tree.getType()
            && ArchiveTreeConstant.VALUE_TYPE_DYNAMIC == tree.getValueType();
    }

    /**
     *
     * @see org.springframework.core.Ordered#getOrder()
     */
    @Override
    public int getOrder() {
        return ArchiveTreeConstant.TYPE_ARCHIVE_TYPE;
    }

    /**
     * @see com.gdda.archives.platform.type.tree.processor.AbstractTreeNodeProcessor#dynamicNodes(com.gdda.archives.platform.app.modular.tree.entity.ArchiveTree,
     *      java.lang.String, java.lang.Long)
     */
    @Override
    protected List<TreeNode> dynamicNodes(ArchiveTree tree, String parentId, Long menuId) {
        final List<ArchiveType> types = archiveTypeSdkService.queryArchiveTypesBySystemIdAndLibraryCode(
            tree.getSystemId(),
            tree.getLibraryCode());
        if (null == types) {
            return Lists.newArrayList();
        }
        return types.stream().map(e -> {
            final ArchiveTreeNodeDTO nodeData = toArchiveTreeNodeDTO(tree, parentId);
            nodeData.setValue(String.valueOf(e.getId()));
            return TreeNode.create()
                .id(tree.getId() + SymbolConstant.DOT + e.getId())
                .name(e.getName())
                .isLeaf(ArchiveTreeConstant.NOCHILD == tree.getChild())
                .data(nodeData);
        }).collect(Collectors.toList());
    }

}
