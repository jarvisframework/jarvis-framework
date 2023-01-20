package com.jarvis.framework.openfeign.web.sdk;

import com.jarvis.framework.core.exception.BusinessException;
import com.jarvis.framework.web.rest.RestResponse;

public interface ClientSdkService {
    default <T> T getResponseBody(RestResponse<T> response) {
        if (response.isSuccess()) {
            return response.getBody();
        } else if ("BIZ".equals(response.getCode())) {
            throw new BusinessException("接口调用出错：" + response.getBody());
        } else {
            throw new RuntimeException("接口调用出错：" + response.getBody());
        }
    }
}
