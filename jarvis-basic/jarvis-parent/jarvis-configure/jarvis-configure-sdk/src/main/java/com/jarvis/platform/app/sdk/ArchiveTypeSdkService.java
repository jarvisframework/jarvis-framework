package com.jarvis.platform.app.sdk;

import com.jarvis.platform.app.modular.type.entity.ArchiveColumn;
import com.jarvis.platform.app.modular.type.entity.ArchiveTable;
import com.jarvis.platform.app.modular.type.entity.ArchiveType;

import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年7月18日
 */
public interface ArchiveTypeSdkService {

    /**
     * 获取档案门类
     *
     * @param id 档案门类ID
     * @return ArchiveType
     */
    ArchiveType getArchiveTypeById(Long id);

    /**
     * 获取档案门类（请使用queryArchiveTypesBySystemIdAndLibraryCode）
     *
     * @param systemId 系统ID
     * @param libraryCode 库类型
     * @return List<ArchiveType>
     */
    @Deprecated
    List<ArchiveType> queryBySystemIdAndLibraryCode(Long systemId, String libraryCode);

    /**
     * 获取档案门类
     *
     * @param systemId 系统ID
     * @param libraryCode 库类型
     * @return List<ArchiveType>
     */
    List<ArchiveType> queryArchiveTypesBySystemIdAndLibraryCode(Long systemId, String libraryCode);

    /**
     * 获取档案门类下所有表
     *
     * @param typeId 档案门类ID
     * @return List
     */
    List<ArchiveTable> queryArchiveTablesByTypeId(Long typeId);

    /**
     * 获取档案门类表
     *
     * @param id 档案门类表ID
     * @return ArchiveTable
     */
    ArchiveTable getArchiveTableById(Long id);

    /**
     * 获取档案门类表字段
     *
     * @param tableId 档案门类表ID
     * @return List
     */
    List<ArchiveColumn> queryArchiveColumnsByTableId(Long tableId);

    /**
     * 获取档案门类表字段
     *
     * @param id id
     * @return ArchiveColumn
     */
    ArchiveColumn getArchiveColumnById(Long id);

}
