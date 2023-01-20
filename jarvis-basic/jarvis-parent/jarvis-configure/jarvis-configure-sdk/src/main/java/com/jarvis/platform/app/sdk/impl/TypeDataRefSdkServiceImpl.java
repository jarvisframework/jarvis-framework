package com.jarvis.platform.app.sdk.impl;

import com.jarvis.framework.openfeign.web.sdk.ClientSdkService;
import com.jarvis.framework.search.DynamicEntityQuery;
import com.jarvis.framework.search.Page;
import com.jarvis.platform.app.sdk.TypeDataRefSdkService;
import com.jarvis.platform.client.configure.feign.RemoteTypeDataRefClient;
import com.jarvis.platform.type.modular.archive.dto.RecycleDeletedDTO;
import com.jarvis.platform.type.modular.archive.dto.UpdateDeletedDTO;
import com.jarvis.platform.type.modular.archive.dto.UpdateUndeletedDTO;
import com.jarvis.platform.type.modular.archive.entity.TypeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年10月13日
 */
@Component
public class TypeDataRefSdkServiceImpl implements TypeDataRefSdkService, ClientSdkService {

    @Autowired
    private RemoteTypeDataRefClient client;

    /**
     *
     * @see com.gdda.archives.platform.app.sdk.TypeDataRefSdkService#insert(java.lang.Long,
     *      com.gdda.archives.platform.type.modular.archive.entity.TypeData)
     */
    @Override
    public TypeData insert(Long tableId, TypeData entity) {
        return getResponseBody(client.insert(tableId, entity));
    }

    /**
     *
     * @see com.gdda.archives.platform.app.sdk.TypeDataRefSdkService#update(java.lang.Long,
     *      com.gdda.archives.platform.type.modular.archive.entity.TypeData)
     */
    @Override
    public TypeData update(Long tableId, TypeData entity) {
        return getResponseBody(client.update(tableId, entity));
    }

    /**
     *
     * @see com.gdda.archives.platform.app.sdk.TypeDataRefSdkService#getById(java.lang.Long, java.lang.Long)
     */
    @Override
    public TypeData getById(Long tableId, Long id) {
        return getResponseBody(client.getById(tableId, id));
    }

    /**
     *
     * @see com.gdda.archives.platform.app.sdk.TypeDataRefSdkService#updateDeleted(java.lang.Long,
     *      com.gdda.archives.platform.type.modular.archive.dto.UpdateDeletedDTO)
     */
    @Override
    public boolean updateDeleted(Long tableId, UpdateDeletedDTO param) {
        return getResponseBody(client.updateDeleted(tableId, param));
    }

    /**
     *
     * @see com.gdda.archives.platform.app.sdk.TypeDataRefSdkService#updateUndeleted(java.lang.Long,
     *      com.gdda.archives.platform.type.modular.archive.dto.UpdateUndeletedDTO)
     */
    @Override
    public boolean updateUndeleted(Long tableId, UpdateUndeletedDTO param) {
        return getResponseBody(client.updateUndeleted(tableId, param));
    }

    /**
     *
     * @see com.gdda.archives.platform.app.sdk.TypeDataRefSdkService#deleteByIds(java.lang.Long, java.util.List)
     */
    @Override
    public boolean deleteByIds(Long tableId, List<Long> ids) {
        return getResponseBody(client.deleteByIds(tableId, ids));
    }

    /**
     * @see com.gdda.archives.platform.app.sdk.TypeDataRefSdkService#recycleDelete(java.lang.Long,
     *      com.gdda.archives.platform.type.modular.archive.dto.RecycleDeletedDTO)
     */
    @Override
    public boolean recycleDelete(Long tableId, RecycleDeletedDTO param) {
        return getResponseBody(client.recycleDelete(tableId, param));
    }

    /**
     *
     * @see com.gdda.archives.platform.app.sdk.TypeDataRefSdkService#page(java.lang.Long,
     *      com.gdda.archives.framework.search.Page, com.gdda.archives.framework.search.DynamicEntityQuery,
     *      java.lang.String)
     */
    @Override
    public Object page(Long tableId, Page page, DynamicEntityQuery criterion, String keyword) {
        return getResponseBody(client.page(tableId, page, criterion, keyword));
    }

    /**
     *
     * @see com.gdda.archives.platform.app.sdk.TypeDataRefSdkService#queryBy(java.lang.Long,
     *      com.gdda.archives.framework.search.DynamicEntityQuery, java.lang.String)
     */
    @Override
    public List<TypeData> queryBy(Long tableId, DynamicEntityQuery criterion, String keyword) {
        return getResponseBody(client.queryBy(tableId, criterion, keyword));
    }

}
