package com.jarvis.platform.client.configure.feign;

import com.jarvis.framework.web.rest.RestResponse;
import com.jarvis.platform.app.constanst.AppConstant;
import com.jarvis.platform.app.modular.tree.entity.ArchiveTree;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 档案树
 * @author ronghui
 * @version 1.0.0 2022年10月24日
 */
@FeignClient(contextId = "configure.feign.RemoteArchiveTreeClient", name = AppConstant.SERVICE_NAME)
public interface RemoteArchiveTreeClient {

    /**
     * 查询菜单绑定的树
     * @param menuId 菜单ID
     * @return RestResponse<List<ArchiveTree>>
     */
    @GetMapping("/ref-tree-menu/{menuId}/trees")
    RestResponse<List<ArchiveTree>> queryTrees(@PathVariable("menuId") Long menuId);

    /**
     * 获取树详情
     * @param id 树ID
     * @return RestResponse<ArchiveTree>
     */
    @GetMapping("/archive-tree/{id}")
    RestResponse<ArchiveTree> getById(@PathVariable("id") Long id);

    /**
     * 根据父节点查询子节点
     * @param parentId 父ID
     * @return RestResponse<ArchiveTree>
     */
    @GetMapping("/archive-tree/{parentId}/query-children")
    RestResponse<List<ArchiveTree>> queryByParentId(@PathVariable("parentId") Long parentId);
}
