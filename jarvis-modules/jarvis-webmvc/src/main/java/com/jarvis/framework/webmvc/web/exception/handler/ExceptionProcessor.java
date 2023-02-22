package com.jarvis.framework.webmvc.web.exception.handler;

import com.jarvis.framework.web.rest.RestResponse;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年7月8日
 */
public interface ExceptionProcessor {

    boolean support(Exception exception);

    RestResponse<?> process(Exception exception);

}
