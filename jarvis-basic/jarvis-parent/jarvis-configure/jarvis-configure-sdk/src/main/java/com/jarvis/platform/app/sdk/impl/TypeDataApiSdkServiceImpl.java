package com.jarvis.platform.app.sdk.impl;

import com.jarvis.framework.mybatis.update.DynamicEntityDelete;
import com.jarvis.framework.mybatis.update.DynamicEntityUpdate;
import com.jarvis.framework.openfeign.web.sdk.ClientSdkService;
import com.jarvis.framework.search.DynamicEntityQuery;
import com.jarvis.platform.app.sdk.TypeDataApiSdkService;
import com.jarvis.platform.client.configure.feign.RemoteTypeDataApiClient;
import com.jarvis.platform.type.modular.archive.dto.TypeDataListDTO;
import com.jarvis.platform.type.modular.archive.entity.TypeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年10月9日
 */
@Component
public class TypeDataApiSdkServiceImpl implements TypeDataApiSdkService, ClientSdkService {

    @Autowired
    private RemoteTypeDataApiClient client;

    /**
     * @see com.gdda.archives.platform.app.sdk.TypeDataApiSdkService#insert(java.lang.Long,
     *      com.gdda.archives.platform.type.modular.archive.entity.TypeData)
     */
    @Override
    public TypeData insert(Long tableId, TypeData entity) {
        return getResponseBody(client.insert(tableId, entity));
    }

    /**
     * @see com.gdda.archives.platform.app.sdk.TypeDataApiSdkService#insertAll(java.lang.Long, java.util.List)
     */
    @Override
    public List<TypeData> insertAll(Long tableId, List<TypeData> entities) {
        return getResponseBody(client.insertAll(tableId, entities));
    }

    /**
     *
     * @see com.gdda.archives.platform.app.sdk.TypeDataApiSdkService#insertMix(java.util.List)
     */
    @Override
    public List<TypeDataListDTO> insertMix(List<TypeDataListDTO> entities) {
        return getResponseBody(client.insertMix(entities));
    }

    /**
     * @see com.gdda.archives.platform.app.sdk.TypeDataApiSdkService#update(java.lang.Long,
     *      com.gdda.archives.platform.type.modular.archive.entity.TypeData)
     */
    @Override
    public TypeData update(Long tableId, TypeData entity) {
        return getResponseBody(client.update(tableId, entity));
    }

    /**
     * @see com.gdda.archives.platform.app.sdk.TypeDataApiSdkService#updateAll(java.lang.Long, java.util.List)
     */
    @Override
    public List<TypeData> updateAll(Long tableId, List<TypeData> entities) {
        return getResponseBody(client.updateAll(tableId, entities));
    }

    /**
     * @see com.gdda.archives.platform.app.sdk.TypeDataApiSdkService#updateBy(java.lang.Long,
     *      com.gdda.archives.framework.mybatis.update.DynamicEntityUpdate)
     */
    @Override
    public int updateBy(Long tableId, DynamicEntityUpdate criterion) {
        return getResponseBody(client.updateBy(tableId, criterion));
    }

    /**
     * @see com.gdda.archives.platform.app.sdk.TypeDataApiSdkService#deleteById(java.lang.Long, java.lang.Long)
     */
    @Override
    public boolean deleteById(Long tableId, Long id) {
        return getResponseBody(client.deleteById(tableId, id));
    }

    /**
     * @see com.gdda.archives.platform.app.sdk.TypeDataApiSdkService#deleteByIds(java.lang.Long, java.util.List)
     */
    @Override
    public int deleteByIds(Long tableId, List<Long> ids) {
        return getResponseBody(client.deleteByIds(tableId, ids));
    }

    /**
     * @see com.gdda.archives.platform.app.sdk.TypeDataApiSdkService#deleteBy(java.lang.Long,
     *      com.gdda.archives.framework.mybatis.update.DynamicEntityDelete)
     */
    @Override
    public int deleteBy(Long tableId, DynamicEntityDelete criterion) {
        return getResponseBody(client.deleteBy(tableId, criterion));
    }

    /**
     * @see com.gdda.archives.platform.app.sdk.TypeDataApiSdkService#getById(java.lang.Long, java.lang.Long)
     */
    @Override
    public TypeData getById(Long tableId, Long id) {
        return getResponseBody(client.getById(tableId, id));
    }

    /**
     * @see com.gdda.archives.platform.app.sdk.TypeDataApiSdkService#getBy(java.lang.Long,
     *      com.gdda.archives.framework.search.DynamicEntityQuery)
     */
    @Override
    public TypeData getBy(Long tableId, DynamicEntityQuery criterion) {
        return getResponseBody(client.getBy(tableId, criterion));
    }

    /**
     *
     * @see com.gdda.archives.platform.app.sdk.TypeDataApiSdkService#queryBy(java.lang.Long,
     *      com.gdda.archives.framework.search.DynamicEntityQuery)
     */
    @Override
    public List<TypeData> queryBy(Long tableId, DynamicEntityQuery criterion) {
        return getResponseBody(client.queryBy(tableId, criterion));
    }

}
