package com.jarvis.platform.client.configure.feign;

import com.jarvis.framework.mybatis.update.DynamicEntityDelete;
import com.jarvis.framework.mybatis.update.DynamicEntityUpdate;
import com.jarvis.framework.search.DynamicEntityQuery;
import com.jarvis.framework.web.rest.RestResponse;
import com.jarvis.platform.app.constanst.AppConstant;
import com.jarvis.platform.type.modular.archive.dto.TypeDataListDTO;
import com.jarvis.platform.type.modular.archive.entity.TypeData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 档案门类操作（所有操作不会级联）
 *
 * @author qiucs
 * @version 1.0.0 2022年10月9日
 */
@FeignClient(contextId = "configure.feign.RemoteTypeDataApiClient", name = AppConstant.SERVICE_NAME,
    path = "/type-data-api")
public interface RemoteTypeDataApiClient {

    /**
     * 新增
     *
     * @param tableId 表ID
     * @param entity 数据
     * @return RestResponse<TypeData>
     */
    @PostMapping("/{tableId}")
    RestResponse<TypeData> insert(@PathVariable("tableId") Long tableId, @RequestBody TypeData entity);

    /**
     * 新增
     *
     * @param tableId 表ID
     * @param entitites 数据
     * @return RestResponse<List<TypeData>>
     */
    @PostMapping("/{tableId}/list")
    RestResponse<List<TypeData>> insertAll(@PathVariable("tableId") Long tableId, @RequestBody List<TypeData> entities);

    /**
     * 新增
     *
     * @param entitites 数据
     * @return RestResponse<List<TypeDataListDTO>>
     */
    @PostMapping("/{tableId}/mix")
    RestResponse<List<TypeDataListDTO>> insertMix(@RequestBody List<TypeDataListDTO> entities);

    /**
     * 修改
     *
     * @param tableId 表ID
     * @param entity 数据
     * @return RestResponse<TypeData>
     */
    @PutMapping("/{tableId}")
    RestResponse<TypeData> update(@PathVariable("tableId") Long tableId, @RequestBody TypeData entity);

    /**
     * 修改
     *
     * @param tableId 表ID
     * @param entities 数据
     * @return RestResponse<TypeData>
     */
    @PutMapping("/{tableId}/list")
    RestResponse<List<TypeData>> updateAll(@PathVariable("tableId") Long tableId, @RequestBody List<TypeData> entities);

    /**
     * 物理删除
     *
     * @param tableId 表ID
     * @param criterion 条件
     * @return RestResponse<TypeData>
     */
    @PutMapping("/{tableId}/criterion")
    RestResponse<Integer> updateBy(@PathVariable("tableId") Long tableId, @RequestBody DynamicEntityUpdate criterion);

    /**
     * 物理删除
     *
     * @param tableId 表ID
     * @param id ID
     * @return RestResponse<TypeData>
     */
    @DeleteMapping("/{tableId}/{id}")
    RestResponse<Boolean> deleteById(@PathVariable("tableId") Long tableId, @PathVariable("id") Long id);

    /**
     * 物理删除
     *
     * @param tableId 表ID
     * @param ids ID集合
     * @return RestResponse<TypeData>
     */
    @DeleteMapping("/{tableId}")
    RestResponse<Integer> deleteByIds(@PathVariable("tableId") Long tableId, @RequestBody List<Long> ids);

    /**
     * 物理删除
     *
     * @param tableId 表ID
     * @param criterion 条件
     * @return RestResponse<TypeData>
     */
    @DeleteMapping("/{tableId}/criterion")
    RestResponse<Integer> deleteBy(@PathVariable("tableId") Long tableId, @RequestBody DynamicEntityDelete criterion);

    /**
     * 获取数据（单条）
     *
     * @param tableId 表ID
     * @param id ID
     * @return RestResponse<TypeData>
     */
    @GetMapping("/{tableId}/{id}")
    RestResponse<TypeData> getById(@PathVariable("tableId") Long tableId, @PathVariable("tableId") Long id);

    /**
     * 获取数据（单条）
     *
     * @param tableId 表ID
     * @param criterion 条件
     * @return RestResponse<TypeData>
     */
    @PostMapping("/{tableId}/criterion/one")
    RestResponse<TypeData> getBy(@PathVariable("tableId") Long tableId, @RequestBody DynamicEntityQuery criterion);

    /**
     * 获取数据（集合）
     *
     * @param tableId 表ID
     * @param criterion 条件
     * @return RestResponse<List<TypeData>>
     */
    @PostMapping("/{tableId}/criterion/list")
    RestResponse<List<TypeData>> queryBy(@PathVariable("tableId") Long tableId,
        @RequestBody DynamicEntityQuery criterion);

    /**
     * 是否存在
     *
     * @param tableId 表ID
     * @param criterion 条件
     * @return RestResponse<Boolean>
     */
    @PostMapping("/{tableId}/criterion/exists")
    RestResponse<Boolean> exists(@PathVariable("tableId") Long tableId, @RequestBody DynamicEntityQuery criterion);
}
