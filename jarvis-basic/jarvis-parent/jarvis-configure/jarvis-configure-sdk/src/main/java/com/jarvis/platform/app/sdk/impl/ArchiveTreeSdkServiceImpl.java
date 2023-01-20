package com.jarvis.platform.app.sdk.impl;

import com.jarvis.framework.openfeign.web.sdk.ClientSdkService;
import com.jarvis.platform.app.modular.tree.entity.ArchiveTree;
import com.jarvis.platform.app.sdk.ArchiveTreeSdkService;
import com.jarvis.platform.client.configure.feign.RemoteArchiveTreeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 档案树
 * @author ronghui
 * @version 1.0.0 2022年10月24日
 */
@Component
public class ArchiveTreeSdkServiceImpl implements ArchiveTreeSdkService, ClientSdkService {

    @Autowired
    private RemoteArchiveTreeClient client;

    /**
     * 查询菜单绑定的树
     * @param menuId 菜单ID
     * @return List<ArchiveTree>
     */
    @Override
    public List<ArchiveTree> queryTrees(Long menuId) {
        return getResponseBody(client.queryTrees(menuId));
    }

    /**
     * 获取树详情
     * @param id 树ID
     * @return ArchiveTree
     */
    @Override
    public ArchiveTree getById(Long id) {
        return getResponseBody(client.getById(id));
    }

    /**
     * 根据父节点查询子节点
     * @param parentId 父ID
     * @return ArchiveTree
     */
    @Override
    public List<ArchiveTree> queryByParentId(Long parentId) {
        return getResponseBody(client.queryByParentId(parentId));
    }
}
