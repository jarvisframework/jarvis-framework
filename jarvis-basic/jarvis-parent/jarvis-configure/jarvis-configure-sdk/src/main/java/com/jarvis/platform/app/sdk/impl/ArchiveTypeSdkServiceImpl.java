package com.jarvis.platform.app.sdk.impl;

import com.jarvis.framework.core.exception.BusinessException;
import com.jarvis.framework.web.rest.RestResponse;
import com.jarvis.platform.app.constanst.AppCacheKeyConstant;
import com.jarvis.platform.app.modular.type.entity.ArchiveColumn;
import com.jarvis.platform.app.modular.type.entity.ArchiveTable;
import com.jarvis.platform.app.modular.type.entity.ArchiveType;
import com.jarvis.platform.app.sdk.ArchiveTypeSdkService;
import com.jarvis.platform.client.configure.feign.RemoteArchiveTypeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年7月18日
 */
@Component
public class ArchiveTypeSdkServiceImpl implements ArchiveTypeSdkService {

    @Autowired
    private RemoteArchiveTypeClient client;

    private <T> T getResponseBody(RestResponse<T> response) {
        if (response.isSuccess()) {
            return response.getBody();
        }
        throw new BusinessException("接口调用出错：" + response.getBody());
    }

    /**
     *
     * @see com.gdda.archives.platform.app.sdk.ArchiveTypeSdkService#getArchiveTypeById(java.lang.Long)
     */
    @Override
    @Cacheable(cacheNames = AppCacheKeyConstant.ARCHIVE_TYPE, key = "#id", unless = "#result == null")
    public ArchiveType getArchiveTypeById(Long id) {
        final RestResponse<ArchiveType> response = client.getArchiveTypeById(id);
        return getResponseBody(response);
    }

    @Override
    public List<ArchiveType> queryBySystemIdAndLibraryCode(Long systemId, String libraryCode) {
        return queryArchiveTypesBySystemIdAndLibraryCode(systemId, libraryCode);
    }

    /**
     * @see com.gdda.archives.platform.app.sdk.ArchiveTypeSdkService#queryArchiveTypesBySystemIdAndLibraryCode(java.lang.Long,
     *      java.lang.String)
     */
    @Override
    public List<ArchiveType> queryArchiveTypesBySystemIdAndLibraryCode(Long systemId, String libraryCode) {
        return getResponseBody(client.queryArchiveTypesBySystemIdAndLibraryCode(systemId, libraryCode));
    }

    /**
     *
     * @see com.gdda.archives.platform.app.sdk.ArchiveTypeSdkService#queryArchiveTablesByTypeId(java.lang.Long)
     */
    @Override
    @Cacheable(cacheNames = AppCacheKeyConstant.ARCHIVE_TYPE_TABLES, key = "#typeId", unless = "#result == null")
    public List<ArchiveTable> queryArchiveTablesByTypeId(Long typeId) {
        final RestResponse<List<ArchiveTable>> response = client.queryArchiveTablesByTypeId(typeId);
        return getResponseBody(response);
    }

    /**
     *
     * @see com.gdda.archives.platform.app.sdk.ArchiveTypeSdkService#getArchiveTableById(java.lang.Long)
     */
    @Override
    @Cacheable(cacheNames = AppCacheKeyConstant.ARCHIVE_TABLE, key = "#id", unless = "#result == null")
    public ArchiveTable getArchiveTableById(Long id) {
        final RestResponse<ArchiveTable> response = client.getArchiveTableById(id);
        return getResponseBody(response);
    }

    /**
     *
     * @see com.gdda.archives.platform.app.sdk.ArchiveTypeSdkService#queryArchiveColumnsByTableId(java.lang.Long)
     */
    @Override
    @Cacheable(cacheNames = AppCacheKeyConstant.ARCHIVE_TABLE_COLUMNS, key = "#tableId", unless = "#result == null")
    public List<ArchiveColumn> queryArchiveColumnsByTableId(Long tableId) {
        final RestResponse<List<ArchiveColumn>> response = client.queryArchiveColumnsByTableId(tableId);
        return getResponseBody(response);
    }

    /**
     *
     * @see com.gdda.archives.platform.app.sdk.ArchiveTypeSdkService#getArchiveColumnById(java.lang.Long)
     */
    @Override
    @Cacheable(cacheNames = AppCacheKeyConstant.ARCHIVE_COLUMN, key = "#id", unless = "#result == null")
    public ArchiveColumn getArchiveColumnById(Long id) {
        return getResponseBody(client.getArchiveColumnById(id));
    }

}
