package com.jarvis.platform.client.configure.feign;

import com.jarvis.framework.search.DynamicEntityQuery;
import com.jarvis.framework.search.Page;
import com.jarvis.framework.web.rest.RestResponse;
import com.jarvis.platform.app.constanst.AppConstant;
import com.jarvis.platform.type.modular.archive.dto.RecycleDeletedDTO;
import com.jarvis.platform.type.modular.archive.dto.UpdateDeletedDTO;
import com.jarvis.platform.type.modular.archive.dto.UpdateUndeletedDTO;
import com.jarvis.platform.type.modular.archive.entity.TypeData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 档案门类操作（所有操作会级联操作）
 *
 * @author qiucs
 * @version 1.0.0 2022年10月13日
 */
@FeignClient(contextId = "configure.feign.RemoteTypeDataRefClient", name = AppConstant.SERVICE_NAME,
    path = "/type-data")
public interface RemoteTypeDataRefClient {

    /**
     * 新增
     *
     * @param tableId 表ID
     * @param entity 数据
     * @return RestResponse
     */
    @PostMapping("/{tableId}")
    public RestResponse<TypeData> insert(@PathVariable("tableId") Long tableId, @RequestBody TypeData entity);

    /**
     * 修改
     *
     * @param tableId 表ID
     * @param entity 数据
     * @return RestResponse
     */
    @PutMapping("/{tableId}")
    public RestResponse<TypeData> update(@PathVariable("tableId") Long tableId, @RequestBody TypeData entity);

    /**
     * 逻辑删除
     *
     * @param tableId 表ID
     * @param param 参数
     * @return RestResponse
     */
    @PostMapping("/{tableId}/deleted")
    public RestResponse<Boolean> updateDeleted(@PathVariable("tableId") Long tableId,
                                               @RequestBody UpdateDeletedDTO param);

    /**
     * 还原逻辑删除
     *
     * @param tableId 表ID
     * @param param 参数
     * @return RestResponse
     */
    @PostMapping("/{tableId}/undeleted")
    public RestResponse<Boolean> updateUndeleted(@PathVariable("tableId") Long tableId,
        @RequestBody UpdateUndeletedDTO param);

    /**
     * 回收站删除（优先按ids删除，如果ids为null，则按条件删除）
     *
     * @param tableId 表ID
     * @param ids ids
     * @return RestResponse
     */
    @DeleteMapping("/{tableId}/recycle")
    public RestResponse<Boolean> recycleDelete(@PathVariable("tableId") Long tableId,
        @RequestBody RecycleDeletedDTO param);

    /**
     * 物理删除
     *
     * @param tableId 表ID
     * @param ids ids
     * @return RestResponse<Boolean>
     */
    @DeleteMapping("/{tableId}")
    public RestResponse<Boolean> deleteByIds(@PathVariable("tableId") Long tableId, @RequestBody List<Long> ids);

    /**
     * 获取数据
     *
     * @param tableId 表ID
     * @param id id
     * @return RestResponse<TypeData>
     */
    @GetMapping("/{tableId}/{id}")
    public RestResponse<TypeData> getById(@PathVariable("tableId") Long tableId, @PathVariable("id") Long id);

    /**
     * 分页查询
     *
     * @param tableId 表ID
     * @param page 分页信息
     * @param criterion 条件
     * @param keyword 关键字
     * @return RestResponse
     */
    @PostMapping("/{tableId}/page")
    public RestResponse<?> page(@PathVariable("tableId") Long tableId, @SpringQueryMap Page page,
                                @RequestBody DynamicEntityQuery criterion, @RequestParam("keyword") String keyword);

    /**
     * 分页查询
     *
     * @param tableId 表ID
     * @param criterion 条件
     * @param keyword 关键字
     * @return RestResponse
     */
    @PostMapping("/{tableId}/list")
    public RestResponse<List<TypeData>> queryBy(@PathVariable("tableId") Long tableId,
        @RequestBody DynamicEntityQuery criterion, @RequestParam("keyword") String keyword);

}
