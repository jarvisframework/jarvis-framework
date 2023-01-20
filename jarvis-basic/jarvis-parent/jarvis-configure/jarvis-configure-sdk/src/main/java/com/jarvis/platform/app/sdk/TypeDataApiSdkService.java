package com.jarvis.platform.app.sdk;

import com.jarvis.framework.mybatis.update.DynamicEntityDelete;
import com.jarvis.framework.mybatis.update.DynamicEntityUpdate;
import com.jarvis.framework.search.DynamicEntityQuery;
import com.jarvis.platform.type.modular.archive.dto.TypeDataListDTO;
import com.jarvis.platform.type.modular.archive.entity.TypeData;

import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年10月9日
 */
public interface TypeDataApiSdkService {

    /**
     * 新增
     *
     * @param tableId 表ID
     * @param entity 数据
     * @return TypeData
     */
    TypeData insert(Long tableId, TypeData entity);

    /**
     * 新增
     *
     * @param tableId 表ID
     * @param entities 数据
     * @return List<TypeData>
     */
    List<TypeData> insertAll(Long tableId, List<TypeData> entities);

    /**
     * 新增
     *
     * @param entities 数据
     * @return List<TypeDataListDTO>
     */
    List<TypeDataListDTO> insertMix(List<TypeDataListDTO> entities);

    /**
     * 修改
     *
     * @param tableId 表ID
     * @param entity 数据
     * @return TypeData
     */
    TypeData update(Long tableId, TypeData entity);

    /**
     * 修改
     *
     * @param tableId 表ID
     * @param entities 数据
     * @return TypeData
     */
    List<TypeData> updateAll(Long tableId, List<TypeData> entities);

    /**
     * 修改
     *
     * @param tableId 表ID
     * @param criterion 条件
     * @return int
     */
    int updateBy(Long tableId, DynamicEntityUpdate criterion);

    /**
     * 删除
     *
     * @param tableId 表ID
     * @param id 数据ID
     * @return int
     */
    boolean deleteById(Long tableId, Long id);

    /**
     * 删除
     *
     * @param tableId 表ID
     * @param ids 数据ID集合
     * @return int
     */
    int deleteByIds(Long tableId, List<Long> ids);

    /**
     * 删除
     *
     * @param tableId 表ID
     * @param criterion 条件
     * @return int
     */
    int deleteBy(Long tableId, DynamicEntityDelete criterion);

    /**
     * 获取数据（单条）
     *
     * @param tableId 表ID
     * @param id ID
     * @return TypeData
     */
    TypeData getById(Long tableId, Long id);

    /**
     * 获取数据（单条）
     *
     * @param tableId 表ID
     * @param criterion 条件
     * @return TypeData
     */
    TypeData getBy(Long tableId, DynamicEntityQuery criterion);

    /**
     * 获取数据（集合）
     *
     * @param tableId 表ID
     * @param criterion 条件
     * @return List<TypeData>
     */
    List<TypeData> queryBy(Long tableId, DynamicEntityQuery criterion);

}
