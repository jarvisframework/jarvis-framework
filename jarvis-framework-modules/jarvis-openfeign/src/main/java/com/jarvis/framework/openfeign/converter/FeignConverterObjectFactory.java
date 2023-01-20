package com.jarvis.framework.openfeign.converter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;

public class FeignConverterObjectFactory implements ObjectFactory<HttpMessageConverters> {
    private final ObjectFactory<HttpMessageConverters> objectFactory;
    private FeignHttpMessageConverters feignHttpMessageConverters;

    public FeignConverterObjectFactory(ObjectFactory<HttpMessageConverters> objectFactory) {
        this.objectFactory = objectFactory;
    }

    public HttpMessageConverters getObject() throws BeansException {
        if (null == this.feignHttpMessageConverters) {
            this.init();
        }

        return this.feignHttpMessageConverters;
    }

    private synchronized void init() {
        if (null == this.feignHttpMessageConverters) {
            this.feignHttpMessageConverters = new FeignHttpMessageConverters((HttpMessageConverters)this.objectFactory.getObject());
        }
    }
}
