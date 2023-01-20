package com.jarvis.framework.webmvc.web.exception.handler;

import com.jarvis.framework.web.rest.RestResponse;

public interface ExceptionProcessor {

    boolean support(Exception var1);

    RestResponse<?> process(Exception var1);
}
