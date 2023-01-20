package com.jarvis.platform.app.sdk;

import com.jarvis.framework.search.DynamicEntityQuery;
import com.jarvis.framework.search.Page;
import com.jarvis.platform.type.modular.archive.dto.RecycleDeletedDTO;
import com.jarvis.platform.type.modular.archive.dto.UpdateDeletedDTO;
import com.jarvis.platform.type.modular.archive.dto.UpdateUndeletedDTO;
import com.jarvis.platform.type.modular.archive.entity.TypeData;

import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年10月13日
 */
public interface TypeDataRefSdkService {

    /**
     * 插入
     *
     * @param tableId 表ID
     * @param entity 对象
     * @return boolean
     */
    TypeData insert(Long tableId, TypeData entity);

    /**
     * 修改
     *
     * @param tableId 表ID
     * @param entity 对象
     * @return boolean
     */
    TypeData update(Long tableId, TypeData entity);

    /**
     * 获取数据
     *
     * @param tableId 表ID
     * @param id id
     * @return boolean
     */
    TypeData getById(Long tableId, Long id);

    /**
     * 逻辑删除
     *
     * @param tableId 表ID
     * @param param 参数
     * @return boolean
     */
    boolean updateDeleted(Long tableId, UpdateDeletedDTO param);

    /**
     * 还原
     *
     * @param tableId 表ID
     * @param param 对象
     * @return boolean
     */
    boolean updateUndeleted(Long tableId, UpdateUndeletedDTO param);

    /**
     * 物理删除
     *
     * @param tableId 表ID
     * @param ids 对象
     * @return boolean
     */
    boolean deleteByIds(Long tableId, List<Long> ids);

    /**
     * 回收站删除（优先按ids删除，如果ids为null，则按条件删除）
     *
     * @param tableId 表ID
     * @param param 对象
     * @return boolean
     */
    boolean recycleDelete(Long tableId, RecycleDeletedDTO param);

    /**
     * 分页
     *
     * @param tableId 表ID
     * @param page 分页信息
     * @param criterion 条件
     * @param keyword 关键字
     * @return 对象集合
     */
    Object page(Long tableId, Page page, DynamicEntityQuery criterion, String keyword);

    /**
     * 列表
     *
     * @param tableId 表ID
     * @param criterion 条件
     * @param keyword 关键字
     * @return 对象集合
     */
    List<TypeData> queryBy(Long tableId, DynamicEntityQuery criterion, String keyword);

}
