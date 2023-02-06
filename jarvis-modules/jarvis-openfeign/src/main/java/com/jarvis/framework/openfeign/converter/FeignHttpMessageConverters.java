package com.jarvis.framework.openfeign.converter;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年10月21日
 */
public class FeignHttpMessageConverters extends HttpMessageConverters {

    private final HttpMessageConverters httpMessageConverters;

    private List<HttpMessageConverter<?>> converters;

    private final FeignMappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    public FeignHttpMessageConverters(HttpMessageConverters httpMessageConverters) {
        this.httpMessageConverters = httpMessageConverters;
        mappingJackson2HttpMessageConverter = new FeignMappingJackson2HttpMessageConverter(true);
    }

    /**
     *
     * @see org.springframework.boot.autoconfigure.http.HttpMessageConverters#getConverters()
     */
    @Override
    public List<HttpMessageConverter<?>> getConverters() {
        if (null == converters) {
            initConverters();
        }
        return converters;
    }

    private synchronized void initConverters() {
        if (null != converters) {
            return;
        }
        converters = new ArrayList<>();
        converters.add(mappingJackson2HttpMessageConverter);
        for (final HttpMessageConverter<?> converter : httpMessageConverters.getConverters()) {
            if (converter instanceof FeignMappingJackson2HttpMessageConverter) {
                continue;
            }
            converters.add(converter);
        }
    }

}
