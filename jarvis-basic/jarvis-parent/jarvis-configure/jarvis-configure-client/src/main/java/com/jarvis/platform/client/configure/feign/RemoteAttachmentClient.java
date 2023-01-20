package com.jarvis.platform.client.configure.feign;

import com.jarvis.framework.web.rest.RestResponse;
import com.jarvis.platform.app.constanst.AppConstant;
import com.jarvis.platform.app.modular.attachment.entity.Attachment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 附件表操作
 *
 * @author ronghui
 * @version 1.0.0 2022年10月17日
 */
@FeignClient(contextId = "configure.feign.RemoteAttachmentClient", name = AppConstant.SERVICE_NAME,
        path = "/attachment")
public interface RemoteAttachmentClient {

    @GetMapping({ "/{ownerId}/query"})
    RestResponse<List<Attachment>> queryById(@PathVariable("ownerId") Long ownerId);

    @PostMapping({ "/{ownerId}/{type}"})
    RestResponse<List<Attachment>> queryByOwnerIdAndType(@PathVariable("ownerId") Long ownerId, @PathVariable("type") String type);

    @DeleteMapping({ "/{ownerId}/clear"})
    RestResponse<Boolean> clearByOwnerId(@PathVariable("ownerId") Long ownerId);

    @DeleteMapping( {"/clear"})
    RestResponse<Boolean> clearByOwnerId(@RequestBody List<Long> ownerIds);

    @PostMapping
    RestResponse<Attachment> create(Attachment entity);
}
