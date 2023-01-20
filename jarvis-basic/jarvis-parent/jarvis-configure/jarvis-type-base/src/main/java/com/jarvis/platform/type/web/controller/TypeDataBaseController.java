package com.jarvis.platform.type.web.controller;

import com.jarvis.framework.bizlog.annotation.BizLogger;
import com.jarvis.framework.bizlog.constant.BizLevel;
import com.jarvis.framework.constant.WebMvcConstant;
import com.jarvis.framework.core.tree.TreeNode;
import com.jarvis.framework.search.DynamicEntityQuery;
import com.jarvis.framework.web.rest.RestResponse;
import com.jarvis.platform.type.web.service.TypeDataBaseService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.javassist.bytecode.stackmap.TypeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年10月31日
 */
public class TypeDataBaseController<Service extends TypeDataBaseService> {

    @Autowired
    protected Service service;

    @ApiOperation(value = "详情", httpMethod = SwaggerAipHttpMethod.GET)
    @GetMapping("/{tableId}/{id}")
    public RestResponse<TypeData> getById(@PathVariable("tableId") Long tableId, @PathVariable("id") Long id,
                                          @RequestHeader(WebMvcConstant.MENU_HEADER_NAME) Long menuId) {
        return RestResponse.success(service.getById(tableId, id, menuId));
    }

    @ApiOperation(value = "分页", httpMethod = SwaggerAipHttpMethod.GET)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Q_[EQ|LIKE|GT]_[属性名称]", value = "动态条件，可以有多个，如：Q_EQ_status=80"),
        @ApiImplicitParam(name = "keyword", value = "关键字"),
    })
    @GetMapping("/{tableId}/page")
    @BizLogger(level = BizLevel.QUERY, content = "查询第#{args[0].pageNumber}页，每页#{args[0].pageSize}条数，查询关键字：#{args[2]}")
    public RestResponse<?> page(@PathVariable("tableId") Long tableId,
        Page page,
        @ApiParam(hidden = true) DynamicEntityQuery criterion,
        String keyword,
        @RequestHeader(WebMvcConstant.MENU_HEADER_NAME) Long menuId) {
        List<TypeData> result = service.page(tableId, page, criterion, keyword, menuId);
        result = processPageData(result);
        if (page.isCounted()) {
            page.setContent(result);
            return RestResponse.success(page);
        }
        return RestResponse.success(result);
    }

    protected List<TypeData> processPageData(List<TypeData> result) {
        return result;
    }

    @ApiOperation(value = "根节点", httpMethod = SwaggerAipHttpMethod.GET)
    @GetMapping("/root")
    public RestResponse<List<TreeNode>> root(@RequestHeader(WebMvcConstant.MENU_HEADER_NAME) Long menuId) {
        List<TreeNode> result = service.root(menuId);
        result = processRootData(result);
        return RestResponse.success(result);
    }

    protected List<TreeNode> processRootData(List<TreeNode> result) {
        return result;
    }

    @ApiOperation(value = "树节点", httpMethod = SwaggerAipHttpMethod.GET)
    @GetMapping("/{parentId}/tree")
    public RestResponse<List<TreeNode>> tree(@PathVariable("parentId") String parentId,
        @RequestHeader(WebMvcConstant.MENU_HEADER_NAME) Long menuId) {
        List<TreeNode> result = service.tree(parentId, menuId);
        result = processTreeData(result);
        return RestResponse.success(result);
    }

    protected List<TreeNode> processTreeData(List<TreeNode> result) {
        return result;
    }

}
