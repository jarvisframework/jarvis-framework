package com.jarvis.platform.app.sdk;

import com.jarvis.platform.app.modular.attachment.entity.Attachment;

import java.util.List;

/**
 *
 * @author ronghui
 * @version 1.0.0 2022年10月17日
 */
public interface AttachmentSdkService {

    /**
     * 根据ownerId查询
     * @param ownerId
     * @return
     */
    List<Attachment> queryByOwnerId(Long ownerId);

    /**
     * 根据ownerId和Type查询
     * @param ownerId
     * @param type
     * @return
     */
    List<Attachment> queryByOwnerIdAndType(Long ownerId, String type);

    /**
     * 根据ownerId清除
     * @param ownerId
     * @return
     */
    Boolean clearByOwnerId(Long ownerId);

    /**
     * 根据ownerIds清除
     * @param ownerIds
     * @return
     */
    Boolean clearByOwnerId(List<Long> ownerIds);

    /**
     * 新增
     * @param entity
     * @return
     */
    Attachment insert(Attachment entity);
}
