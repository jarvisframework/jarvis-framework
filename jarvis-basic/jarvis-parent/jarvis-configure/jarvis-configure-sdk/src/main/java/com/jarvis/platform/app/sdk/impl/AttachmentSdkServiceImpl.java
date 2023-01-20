package com.jarvis.platform.app.sdk.impl;

import com.jarvis.framework.openfeign.web.sdk.ClientSdkService;
import com.jarvis.platform.app.modular.attachment.entity.Attachment;
import com.jarvis.platform.app.sdk.AttachmentSdkService;
import com.jarvis.platform.client.configure.feign.RemoteAttachmentClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author ronghui
 * @version 1.0.0 2022年10月17日
 */
@Component
public class AttachmentSdkServiceImpl implements AttachmentSdkService, ClientSdkService {

    @Autowired
    private RemoteAttachmentClient client;

    @Override
    public List<Attachment> queryByOwnerId(Long ownerId) {
        return getResponseBody(client.queryById(ownerId));
    }

    @Override
    public List<Attachment> queryByOwnerIdAndType(Long ownerId, String type) {
        return getResponseBody(client.queryByOwnerIdAndType(ownerId, type));
    }

    @Override
    public Boolean clearByOwnerId(Long ownerId) {
        return getResponseBody(client.clearByOwnerId(ownerId));
    }

    @Override
    public Boolean clearByOwnerId(List<Long> ownerIds) {
        return getResponseBody(client.clearByOwnerId(ownerIds));
    }

    @Override
    public Attachment insert(Attachment entity) {
        return getResponseBody(client.create(entity));
    }
}
