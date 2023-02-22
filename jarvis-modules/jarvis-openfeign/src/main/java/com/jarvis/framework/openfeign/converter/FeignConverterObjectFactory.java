package com.jarvis.framework.openfeign.converter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年10月21日
 */
public class FeignConverterObjectFactory implements ObjectFactory<HttpMessageConverters> {

    private final ObjectFactory<HttpMessageConverters> objectFactory;

    private FeignHttpMessageConverters feignHttpMessageConverters;

    public FeignConverterObjectFactory(ObjectFactory<HttpMessageConverters> objectFactory) {
        this.objectFactory = objectFactory;
    }

    /**
     *
     * @see org.springframework.beans.factory.ObjectFactory#getObject()
     */
    @Override
    public HttpMessageConverters getObject() throws BeansException {
        if (null == feignHttpMessageConverters) {
            init();
        }
        return feignHttpMessageConverters;
    }

    private synchronized void init() {
        if (null != feignHttpMessageConverters) {
            return;
        }

        this.feignHttpMessageConverters = new FeignHttpMessageConverters(objectFactory.getObject());
    }

}
