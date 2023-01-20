package com.jarvis.platform.type.tree.processor;

import com.jarvis.framework.core.tree.TreeNode;
import com.jarvis.platform.app.modular.tree.constant.ArchiveTreeConstant;
import com.jarvis.platform.app.modular.tree.dto.ArchiveTreeNodeDTO;
import com.jarvis.platform.app.modular.tree.entity.ArchiveTree;
import com.jarvis.platform.app.modular.type.entity.ArchiveColumn;
import com.jarvis.platform.app.sdk.ArchiveTypeSdkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年11月1日
 */
@Component
public class StaticNodeProcessor extends AbstractTreeNodeProcessor implements PriorityOrdered {

    @Autowired
    private ArchiveTypeSdkService archiveTypeSdkService;

    /**
     *
     * @see com.gdda.archives.platform.type.tree.TreeNodeProcessor#support(com.gdda.archives.platform.app.modular.tree.entity.ArchiveTree)
     */
    @Override
    public boolean support(ArchiveTree tree) {
        if (ArchiveTreeConstant.TYPE_COLUMN_DYNAMIC == tree.getType()) {
            return false;
        }
        final Integer valueType = tree.getValueType();
        return null == valueType || ArchiveTreeConstant.VALUE_TYPE_FIXATION == valueType;
    }

    /**
     * @see com.gdda.archives.platform.type.tree.processor.AbstractTreeNodeProcessor#processArchiveTreeNodeDTO(com.gdda.archives.platform.app.modular.tree.dto.ArchiveTreeNodeDTO,
     *      com.gdda.archives.platform.app.modular.tree.entity.ArchiveTree)
     */
    @Override
    protected void processArchiveTreeNodeDTO(ArchiveTreeNodeDTO nodeData, ArchiveTree tree) {
        if (ArchiveTreeConstant.TYPE_COLUMN_STATIC != tree.getType()) {
            return;
        }
        final ArchiveColumn column = archiveTypeSdkService.getArchiveColumnById(tree.getColumnId());
        nodeData.setFilter(toColumnFilter(column.getColumnName(), tree.getValue()));
    }

    /**
     * @see com.gdda.archives.platform.type.tree.processor.AbstractTreeNodeProcessor#dynamicNodes(com.gdda.archives.platform.app.modular.tree.entity.ArchiveTree,
     *      java.lang.String, java.lang.Long)
     */
    @Override
    protected List<TreeNode> dynamicNodes(ArchiveTree tree, String parentId, Long menuId) {
        return null;
    }

    /**
     *
     * @see org.springframework.core.Ordered#getOrder()
     */
    @Override
    public int getOrder() {
        return 0;
    }
