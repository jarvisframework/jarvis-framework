package com.jarvis.framework.openfeign.web.sdk;

import com.jarvis.framework.core.exception.BusinessException;
import com.jarvis.framework.web.rest.RestResponse;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年10月9日
 */
public interface ClientSdkService {

    default <T> T getResponseBody(RestResponse<T> response) {
        if (response.isSuccess()) {
            return response.getBody();
        }
        if (RestResponse.BIZ_CODE.equals(response.getCode())) {
            throw new BusinessException("接口调用出错：" + response.getBody());
        }
        throw new RuntimeException("接口调用出错：" + response.getBody());
    }

}
