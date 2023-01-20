package com.jarvis.platform.type.web.controller;

import com.jarvis.framework.bizlog.annotation.BizLogger;
import com.jarvis.framework.bizlog.constant.BizLevel;
import com.jarvis.framework.web.rest.RestResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年10月31日
 */
public class TypeDataWriteController<Service extends TypeDataBaseService> extends TypeDataBaseController<Service> {

    @ApiOperation(value = "新增", httpMethod = SwaggerAipHttpMethod.POST)
    @PostMapping("/{tableId}")
    @BizLogger(content = "表ID：#{args[0].toString()}，新增内容：#{args[1].toString()}", level = BizLevel.WRITE)
    public RestResponse<?> create(@PathVariable("tableId") Long tableId, @RequestBody TypeData entity,
                                  @RequestHeader(WebMvcConstant.MENU_HEADER_NAME) Long menuId) {
        service.insert(tableId, entity, menuId);
        return RestResponse.success(entity);
    }

    @ApiOperation(value = "修改", httpMethod = SwaggerAipHttpMethod.PUT)
    @PutMapping("/{tableId}")
    @BizLogger(content = "表ID：#{args[0].toString()}，修改内容：#{args[1]}", level = BizLevel.WRITE)
    public RestResponse<?> update(@PathVariable("tableId") Long tableId, @RequestBody TypeData entity,
        @RequestHeader(WebMvcConstant.MENU_HEADER_NAME) Long menuId) {
        service.update(tableId, entity, menuId);
        return RestResponse.success(entity);
    }

    @ApiOperation(value = "逻辑删除", httpMethod = SwaggerAipHttpMethod.DELETE)
    @DeleteMapping("/{tableId}")
    @BizLogger(content = "表ID：#{args[0].toString()}，删除ID：#{args[1]}", level = BizLevel.WRITE)
    public RestResponse<Boolean> updateDeleted(@PathVariable("tableId") Long tableId, @RequestBody List<Long> ids,
        @RequestHeader(WebMvcConstant.MENU_HEADER_NAME) Long menuId) {
        return RestResponse.success(service.updateDeleted(tableId, ids, menuId));
    }
}
