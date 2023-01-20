package com.jarvis.platform.type.tree.processor;

import com.jarvis.framework.constant.BaseColumnConstant;
import com.jarvis.framework.constant.CommonConstant;
import com.jarvis.framework.constant.SymbolConstant;
import com.jarvis.framework.core.tree.TreeNode;
import com.jarvis.framework.search.DynamicEntityQuery;
import com.jarvis.framework.util.CamelCaseUtil;
import com.jarvis.platform.app.modular.tree.constant.ArchiveTreeConstant;
import com.jarvis.platform.app.modular.tree.dto.ArchiveTreeNodeDTO;
import com.jarvis.platform.app.modular.tree.entity.ArchiveTree;
import com.jarvis.platform.app.modular.type.entity.ArchiveColumn;
import com.jarvis.platform.app.sdk.ArchiveTypeSdkService;
import com.jarvis.platform.app.sdk.TypeDataApiSdkService;
import com.jarvis.platform.type.authority.TypeAuthorityProcessor;
import org.apache.ibatis.javassist.bytecode.stackmap.TypeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年11月1日
 */
@Component
public class ColumnDynamicNodeProcessor extends AbstractTreeNodeProcessor implements Ordered {

    private static final String COUNT_ALIAS = "count";

    @Autowired
    private TypeAuthorityProcessor authorityProcessor;

    @Autowired
    private ArchiveTypeSdkService typeSdkService;

    @Autowired
    private TypeDataApiSdkService apiSdkService;

    /**
     *
     * @see com.gdda.archives.platform.type.tree.TreeNodeProcessor#support(com.gdda.archives.platform.app.modular.tree.entity.ArchiveTree)
     */
    @Override
    public boolean support(ArchiveTree tree) {
        if (null == tree.getValueType()) {
            return false;
        }
        return ArchiveTreeConstant.TYPE_COLUMN_DYNAMIC == tree.getType()
            && ArchiveTreeConstant.VALUE_TYPE_DYNAMIC == tree.getValueType();
    }

    /**
     *
     * @see org.springframework.core.Ordered#getOrder()
     */
    @Override
    public int getOrder() {
        return ArchiveTreeConstant.TYPE_COLUMN_DYNAMIC;
    }

    /**
     * @see com.gdda.archives.platform.type.tree.processor.AbstractTreeNodeProcessor#dynamicNodes(com.gdda.archives.platform.app.modular.tree.entity.ArchiveTree,
     *      java.lang.String, java.lang.Long)
     */
    @Override
    protected List<TreeNode> dynamicNodes(ArchiveTree tree, String parentId, Long menuId) {
        final ArchiveColumn column = typeSdkService.getArchiveColumnById(tree.getColumnId());
        final String prop = CamelCaseUtil.toLowerCamelCase(column.getColumnName());
        final boolean counted = null != tree.getCounted() && CommonConstant.YES == tree.getCounted();

        return queryTypeDatas(tree, column, menuId).stream().map(e -> {
            final ArchiveTreeNodeDTO nodeData = toArchiveTreeNodeDTO(tree, parentId);
            final Object object = e.get(prop);
            final String val = String.valueOf(object);
            String name = String.valueOf(val);
            if (counted) {
                name = name + SymbolConstant.OPEN_PARENTHESIS + e.get(COUNT_ALIAS) + SymbolConstant.CLOSE_PARENTHESIS;
            }
            nodeData.setFilter(toColumnFilter(column.getColumnName(), object));
            nodeData.setValue(val);
            return TreeNode.create()
                .id(tree.getId() + SymbolConstant.DOT + val)
                .name(name)
                .isLeaf(ArchiveTreeConstant.NOCHILD == tree.getChild())
                .data(nodeData);
        }).collect(Collectors.toList());
    }

    private List<TypeData> queryTypeDatas(ArchiveTree tree, ArchiveColumn column, Long menuId) {
        final DynamicEntityQuery query = DynamicEntityQuery.create();

        query.columns(column.getColumnName()).filter(f -> {
            f.equal(BaseColumnConstant.DELETED, CommonConstant.NO);
        });

        if (null != tree.getCounted() && CommonConstant.YES == tree.getCounted()) {
            query.count(COUNT_ALIAS);
        }

        if (null != authorityProcessor) {
            authorityProcessor.processDataQuery(tree.getTableId(), menuId, query);
        }

        return apiSdkService.queryBy(tree.getTableId(), query);
    }
