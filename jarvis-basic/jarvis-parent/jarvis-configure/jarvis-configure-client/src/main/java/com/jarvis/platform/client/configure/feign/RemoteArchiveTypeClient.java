package com.jarvis.platform.client.configure.feign;

import com.jarvis.framework.openfeign.annotation.PermitRequest;
import com.jarvis.framework.web.rest.RestResponse;
import com.jarvis.platform.app.constanst.AppConstant;
import com.jarvis.platform.app.modular.type.entity.ArchiveColumn;
import com.jarvis.platform.app.modular.type.entity.ArchiveTable;
import com.jarvis.platform.app.modular.type.entity.ArchiveType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年7月18日
 */
@FeignClient(contextId = "configure.feign.RemoteArchiveTypeClient", name = AppConstant.SERVICE_NAME)
public interface RemoteArchiveTypeClient {

    /**
     * 获取档案门类
     *
     * @param id 档案门类ID
     * @return RestResponse<ArchiveType>
     */
    @GetMapping("/archive-type/{id}")
    @PermitRequest
    RestResponse<ArchiveType> getArchiveTypeById(@PathVariable("id") Long id);

    /**
     * 获取档案门类
     *
     * @param systemId 系统ID
     * @param libraryCode 库类型
     * @return RestResponse<List<ArchiveType>>
     */
    @GetMapping("/archive-type/{systemId}/{libraryCode}/list")
    RestResponse<List<ArchiveType>> queryArchiveTypesBySystemIdAndLibraryCode(@PathVariable("systemId") Long systemId,
        @PathVariable("libraryCode") String libraryCode);

    /**
     * 获取档案门类下所有表
     *
     * @param typeId 档案门类ID
     * @return RestResponse<List<ArchiveTable>>
     */
    @GetMapping("/archive-table/{typeId}/list")
    @PermitRequest
    RestResponse<List<ArchiveTable>> queryArchiveTablesByTypeId(@PathVariable("typeId") Long typeId);

    /**
     * 获取档案门类表
     *
     * @param id 档案门类表ID
     * @return RestResponse<ArchiveTable>
     */
    @GetMapping("/archive-table/{id}")
    @PermitRequest
    RestResponse<ArchiveTable> getArchiveTableById(@PathVariable("id") Long id);

    /**
     * 获取档案门类表字段
     *
     * @param tableId 档案门类表ID
     * @return RestResponse<List<ArchiveColumn>>
     */
    @GetMapping("/archive-column/{tableId}/list")
    @PermitRequest
    RestResponse<List<ArchiveColumn>> queryArchiveColumnsByTableId(@PathVariable("tableId") Long tableId);

    /**
     * 获取档案门类表字段
     *
     * @param id 档案门类表字段ID
     * @return RestResponse<ArchiveColumn>
     */
    @GetMapping("/archive-column/{id}")
    @PermitRequest
    RestResponse<ArchiveColumn> getArchiveColumnById(@PathVariable("id") Long id);

}
