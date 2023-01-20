package com.jarvis.platform.app.sdk;

import com.jarvis.platform.app.modular.tree.entity.ArchiveTree;

import java.util.List;

/**
 * 档案树
 * @author ronghui
 * @version 1.0.0 2022年10月24日
 */
public interface ArchiveTreeSdkService {

    /**
     * 查询菜单绑定的树
     * @param menuId 菜单ID
     * @return List<ArchiveTree>
     */
    List<ArchiveTree> queryTrees(Long menuId);

    /**
     * 获取树详情
     * @param id 树ID
     * @return ArchiveTree
     */
    ArchiveTree getById(Long id);

    /**
     * 根据父节点查询子节点
     * @param parentId 父ID
     * @return ArchiveTree
     */
    List<ArchiveTree> queryByParentId(Long parentId);
}
